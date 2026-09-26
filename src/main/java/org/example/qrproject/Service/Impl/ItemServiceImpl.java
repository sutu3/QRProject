package org.example.qrproject.Service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.qrproject.Exception.AppException;
import org.example.qrproject.Exception.ErrorCode;
import org.example.qrproject.Helper.QrCodeGenerator;
import org.example.qrproject.Module.AttributeEntity;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.qrproject.Dtos.Request.Item.ItemRequest;
import org.example.qrproject.Dtos.Response.Item.ItemResponse;
import org.example.qrproject.Helper.SecurityUtils;
import org.example.qrproject.Mapper.ItemMapper;
import org.example.qrproject.Module.AccountEntity;
import org.example.qrproject.Module.ItemAttributeValueEntity;
import org.example.qrproject.Module.ItemEntity;
import org.example.qrproject.Repo.CategoryAttributeRepo;
import org.example.qrproject.Repo.ItemRepo;
import org.example.qrproject.Service.AccountService;
import org.example.qrproject.Service.CategoryService;
import org.example.qrproject.Service.ItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemServiceImpl implements ItemService {
    CategoryAttributeRepo categoryAttributeRepo;
    AccountService accountService;
    CategoryService categoryService;
    ItemRepo itemRepo;
    ItemMapper itemMapper;
    ObjectMapper objectMapper;
    QrCodeGenerator qrCodeGenerator;


    @Override
    @Transactional
    public ItemResponse create(ItemRequest req) {
        var account = SecurityUtils.getClaim("sub");
        AccountEntity accountEntity=accountService.getAccountById(account);
        var category = categoryService.getOwnedCategory(req.getCategoryId());
        ItemEntity item=itemMapper.toEntity(req);
        item.setCreatedBy(accountEntity);
        item.setCategory(category);
        applyAndValidateValues(item, req.getAttributeValues());
        return itemMapper.toResponse(itemRepo.save(item));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> list(String categoryId) {
        var account = SecurityUtils.getClaim("sub");
        AccountEntity accountEntity=accountService.getAccountById(account);
        var items = categoryId == null
                ? itemRepo.findByCreatedBy_IdAccountAndIsDeletedFalse(accountEntity.getIdAccount())
                : itemRepo.findByCreatedBy_IdAccountAndCategory_IdCategoryAndIsDeletedFalse(accountEntity.getIdAccount(), categoryId);
        return items.stream().map(itemMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ItemResponse update(String id, ItemRequest req) {
        var item = getOwned(id);
        var category = categoryService.getOwnedCategory(req.getCategoryId());
        item.setItemName(req.getItemName());
        item.setDescription(req.getDescription());
        item.setCategory(category);
        item.getValues().clear();
        applyAndValidateValues(item, req.getAttributeValues());
        return itemMapper.toResponse(itemRepo.save(item));
    }

    @Override
    @Transactional
    public void markPrinted(String id) {
        var item = getOwned(id);
        item.setPrinted(true);
        itemRepo.save(item);
    }

    @Override
    @Transactional
    public void delete(String id) {
        var item = getOwned(id);
        item.setIsDeleted(true);
        item.setDeletedAt(LocalDateTime.now());
        itemRepo.save(item);
    }

    @Override
    public byte[] generateQrPng(String id, int size) {
        return qrCodeGenerator.generatePng(buildPayload(getOwned(id)), size);
    }
    private String buildPayload(ItemEntity item) {
        StringBuilder sb = new StringBuilder("code=").append(item.getCode())
                .append(";name=").append(item.getItemName());
        item.getValues().forEach(a ->
                sb.append(";").append(a.getAttribute()).append("=").append(a.getValue()));
        return sb.toString();
    }

    @Override
    public ItemResponse resolveByCode(String code) {
        var item = itemRepo.findByCodeAndIsDeletedFalse(code)
                .orElseThrow(() -> new AppException(ErrorCode.QR_CODE_NOT_FOUND));
        return itemMapper.toResponse(item);
    }

    @Override
    public ItemEntity getOwned(String id) {
        var account = SecurityUtils.getClaim("sub");
        AccountEntity accountEntity = accountService.getAccountById(account);
        return itemRepo.findByIdItemAndCreatedBy_IdAccountAndIsDeletedFalse(
                        id, accountEntity.getIdAccount())
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));
    }
    // ===== RÀNG BUỘC THUỘC TÍNH =====
    private void applyAndValidateValues(ItemEntity item, Map<String, String> input) {
        Map<String, String> values = input == null ? Map.of() : input;
        var allowed = categoryAttributeRepo
                .findByCategory_IdCategoryAndIsDeletedFalseOrderBySortOrderAsc(item.getCategory().getIdCategory())
                .stream()
                .collect(Collectors.toMap(ca -> ca.getAttribute().getCode(), ca -> ca,
                        (a, b) -> a, LinkedHashMap::new));

        // từ chối thuộc tính không thuộc bộ của loại
        for (String key : values.keySet()) {
            if (!allowed.containsKey(key))
                throw new AppException(ErrorCode.ATTRIBUTE_NOT_ALLOWED);
        }

        for (var ca : allowed.values()) {
            var def = ca.getAttribute();
            String val = values.get(def.getCode());
            if ((val == null || val.isBlank()) && ca.getDefaultValue() != null)
                val = ca.getDefaultValue();                    // áp default
            if (ca.isRequired() && (val == null || val.isBlank()))
                throw new AppException(ErrorCode.ATTRIBUTE_MISSING_REQUIRED);
            if (val != null && !val.isBlank()) {
                validateValue(def, val);                        // validate theo dataType + regex
                item.getValues().add(ItemAttributeValueEntity.builder()
                        .item(item).attribute(def).value(val).build());
            }
        }
    }
    private void validateValue(org.example.qrproject.Module.AttributeEntity def, String val) {
        try {
            switch (def.getDataType()) {
                case NUMBER -> new BigInteger(val.trim());
                case DECIMAL -> new BigDecimal(val.trim());
                case BOOLEAN -> {
                    if (!val.equalsIgnoreCase("true") && !val.equalsIgnoreCase("false"))
                        throw new AppException(ErrorCode.ATTRIBUTE_INVALID_VALUE);
                }
                case DATE -> LocalDate.parse(val.trim());
                case DATETIME -> LocalDateTime.parse(val.trim());
                case SELECT -> {
                    if (!optionsOf(def).contains(val.trim()))
                        throw new AppException(ErrorCode.ATTRIBUTE_VALUE_NOT_IN_OPTIONS);
                }
                case MULTI_SELECT -> {
                    var opts = optionsOf(def);
                    for (String v : val.split(","))
                        if (!opts.contains(v.trim()))
                            throw new AppException(ErrorCode.ATTRIBUTE_VALUE_NOT_IN_OPTIONS);
                }
                default -> { /* TEXT, TEXTAREA: không ràng buộc kiểu */ }
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.ATTRIBUTE_INVALID_VALUE);
        }
        if (def.getValidationRegex() != null && !def.getValidationRegex().isBlank()
                && !Pattern.matches(def.getValidationRegex(), val))
            throw new AppException(ErrorCode.ATTRIBUTE_INVALID_VALUE);
    }

    private List<String> optionsOf(AttributeEntity def) {
        if (def.getOptions() == null || def.getOptions().isBlank())
            throw new AppException(ErrorCode.ATTRIBUTE_NO_OPTIONS);
        try {
            return objectMapper.readValue(def.getOptions(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (Exception e) {
            throw new AppException(ErrorCode.ATTRIBUTE_INVALID_OPTIONS);
        }
    }
}

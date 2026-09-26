package org.example.qrproject.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.example.qrproject.Dtos.Request.Attribute.AttributeRequest;
import org.example.qrproject.Dtos.Response.Attribute.AttributeResponse;
import org.example.qrproject.Enum.DataType;
import org.example.qrproject.Exception.AppException;
import org.example.qrproject.Exception.ErrorCode;
import org.example.qrproject.Helper.SecurityUtils;
import org.example.qrproject.Mapper.AttributeMapper;
import org.example.qrproject.Module.AccountEntity;
import org.example.qrproject.Module.AttributeEntity;
import org.example.qrproject.Repo.AttributeRepo;
import org.example.qrproject.Service.AccountService;
import org.example.qrproject.Service.AttributeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeServiceImpl implements AttributeService {
    AttributeRepo attributeRepo;
    AccountService accountService;
    AttributeMapper attributeMapper;

    @Override
    public AttributeResponse create(AttributeRequest req) {
        var account = SecurityUtils.getClaim("sub");
        AccountEntity accountEntity=accountService.getAccountById(account);
        if (attributeRepo.existsByAccount_IdAccountAndCodeIgnoreCaseAndIsDeletedFalse(
                accountEntity.getIdAccount(), req.getCode()))
            throw new IllegalArgumentException("Attribute code already exists: " + req.getCode());

        DataType type = parseType(req.getDataType());
        AttributeEntity entity=attributeMapper.toEntity(req);
        entity.setDataType(type);
        entity.setAccount(accountEntity);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setIsDeleted(false);
        return attributeMapper.toResponse(attributeRepo.save(entity));
    }
    @Override
    public List<AttributeResponse> list() {
        var account = SecurityUtils.getClaim("sub");
        AccountEntity accountEntity=accountService.getAccountById(account);
        return attributeRepo
                .findByAccount_IdAccountAndIsDeletedFalse(accountEntity.getIdAccount())
                .stream().map(attributeMapper::toResponse).toList();
    }
    @Override
    public void delete(String id) {
        var e = getOwned(id);
        e.setIsDeleted(true);
        e.setDeletedAt(LocalDateTime.now());
        attributeRepo.save(e);
    }
    @Override
    public AttributeEntity getOwned(String id) {
        var account = SecurityUtils.getClaim("sub");
        AccountEntity accountEntity=accountService.getAccountById(account);
        return attributeRepo.findByIdAttributeAndAccount_IdAccountAndIsDeletedFalse(
                        id, accountEntity.getIdAccount())
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));
    }

    private DataType parseType(String t) {
        if (t == null || t.isBlank()) return DataType.TEXT;
        try { return DataType.valueOf(t.trim().toUpperCase()); }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid dataType: " + t);
        }
    }

}
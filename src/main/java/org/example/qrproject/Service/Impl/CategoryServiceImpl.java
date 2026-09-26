package org.example.qrproject.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.qrproject.Dtos.Request.Category.CategoryAttributeRequest;
import org.example.qrproject.Dtos.Request.Category.CategoryRequest;
import org.example.qrproject.Dtos.Response.Category.CategoryResponse;
import org.example.qrproject.Exception.AppException;
import org.example.qrproject.Exception.ErrorCode;
import org.example.qrproject.Helper.SecurityUtils;
import org.example.qrproject.Mapper.CategoryAttributeMapper;
import org.example.qrproject.Mapper.CategoryMapper;
import org.example.qrproject.Module.AccountEntity;
import org.example.qrproject.Module.CategoryAttributeEntity;
import org.example.qrproject.Module.CategoryEntity;
import org.example.qrproject.Repo.CategoryAttributeRepo;
import org.example.qrproject.Repo.CategoryRepo;
import org.example.qrproject.Service.AccountService;
import org.example.qrproject.Service.AttributeService;
import org.example.qrproject.Service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepo categoryRepo;
    CategoryAttributeRepo categoryAttributeRepo;
    AttributeService attributeService;
    AccountService accountService;
    CategoryAttributeMapper categoryAttributeMapper;
    CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(CategoryRequest req) {

        CategoryEntity category=categoryMapper.toEntity(req);
        categoryRepo.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> list() {
        var account = SecurityUtils.getClaim("sub");
        AccountEntity accountEntity=accountService.getAccountById(account);
        return categoryRepo.findByAccount_IdAccountAndIsDeletedFalse(accountEntity.getIdAccount()).stream()
                .filter(category -> !category.getIsDeleted())
                .map(categoryMapper::toResponse).collect(Collectors.toList());
    }

    // gán một thuộc tính (tái sử dụng) vào loại đồ vật
    @Override
    @Transactional
    public CategoryResponse addAttribute(String categoryId, CategoryAttributeRequest req) {
        var category = getOwnedCategory(categoryId);
        var attribute = attributeService.getOwned(req.getAttribute());
        if (categoryAttributeRepo.existsByCategory_IdCategoryAndAttribute_IdAttributeAndIsDeletedFalse(
                categoryId, attribute.getIdAttribute()))
            throw new AppException(ErrorCode.ATTRIBUTE_EXIST);
        CategoryAttributeEntity entity=categoryAttributeMapper.toEntity(req);
        entity.setCategory(category);
        entity.setAttribute(attribute);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setIsDeleted(false);

        categoryAttributeRepo.save(entity);
        return getDetail(categoryId);
    }

    // bộ thuộc tính của loại — frontend dùng để render form ràng buộc
    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getDetail(String categoryId) {
        var c = getOwnedCategory(categoryId);
        return categoryMapper.toResponse(c);
    }

    @Override
    @Transactional
    public void delete(String id) {
        CategoryEntity c = getOwnedCategory(id);
        c.setIsDeleted(true);
        c.setDeletedAt(LocalDateTime.now());
        categoryRepo.save(c);
    }

    @Override
   public CategoryEntity getOwnedCategory(String id) {
        var account = SecurityUtils.getClaim("sub");
        AccountEntity accountEntity=accountService.getAccountById(account);
        return categoryRepo.findByIdCategoryAndAccount_IdAccountAndIsDeletedFalse(
                        id, accountEntity.getIdAccount())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }


}
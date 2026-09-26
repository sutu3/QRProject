package org.example.qrproject.Service;

import org.example.qrproject.Dtos.Request.Category.CategoryAttributeRequest;
import org.example.qrproject.Dtos.Request.Category.CategoryRequest;
import org.example.qrproject.Dtos.Response.Category.CategoryResponse;
import org.example.qrproject.Module.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    CategoryResponse create(CategoryRequest req);
    List<CategoryResponse> list();
    CategoryResponse addAttribute(String categoryId, CategoryAttributeRequest req);
    CategoryResponse getDetail(String categoryId);
    void delete(String id);
    CategoryEntity getOwnedCategory(String id);
}

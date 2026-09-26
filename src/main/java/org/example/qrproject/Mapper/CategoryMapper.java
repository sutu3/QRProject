package org.example.qrproject.Mapper;

import org.example.qrproject.Dtos.Request.Category.CategoryAttributeRequest;
import org.example.qrproject.Dtos.Request.Category.CategoryRequest;
import org.example.qrproject.Dtos.Response.Attribute.AttributeResponse;
import org.example.qrproject.Dtos.Response.Category.CategoryResponse;
import org.example.qrproject.Module.AttributeEntity;
import org.example.qrproject.Module.CategoryAttributeEntity;
import org.example.qrproject.Module.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    CategoryEntity toEntity(CategoryRequest request);

    //    @Mapping(target = "role",ignore = true)
    CategoryResponse toResponse(CategoryEntity category);
}

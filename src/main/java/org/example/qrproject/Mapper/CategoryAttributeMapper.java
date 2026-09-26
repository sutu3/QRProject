package org.example.qrproject.Mapper;

import org.example.qrproject.Dtos.Request.Attribute.AttributeRequest;
import org.example.qrproject.Dtos.Request.Category.CategoryAttributeRequest;
import org.example.qrproject.Dtos.Response.Attribute.AttributeResponse;
import org.example.qrproject.Dtos.Response.Category.CategoryAttributeResponse;
import org.example.qrproject.Module.AttributeEntity;
import org.example.qrproject.Module.CategoryAttributeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryAttributeMapper {
    @Mapping(target = "attribute", ignore = true)
    @Mapping(target = "category",ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    CategoryAttributeEntity toEntity(CategoryAttributeRequest request);

    //    @Mapping(target = "role",ignore = true)
    CategoryAttributeResponse toResponse(CategoryAttributeEntity categoryAttribute);
}

package org.example.qrproject.Mapper;

import org.example.qrproject.Dtos.Request.Category.CategoryRequest;
import org.example.qrproject.Dtos.Request.Item.ItemRequest;
import org.example.qrproject.Dtos.Response.Category.CategoryResponse;
import org.example.qrproject.Dtos.Response.Item.ItemResponse;
import org.example.qrproject.Module.CategoryEntity;
import org.example.qrproject.Module.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    ItemEntity toEntity(ItemRequest request);

    //    @Mapping(target = "role",ignore = true)
    ItemResponse toResponse(ItemEntity item);
}

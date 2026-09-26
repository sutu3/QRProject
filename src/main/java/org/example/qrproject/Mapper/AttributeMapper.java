package org.example.qrproject.Mapper;

import org.example.qrproject.Dtos.Request.Account.AccountRequest;
import org.example.qrproject.Dtos.Request.Attribute.AttributeRequest;
import org.example.qrproject.Dtos.Response.Account.AccountResponse;
import org.example.qrproject.Dtos.Response.Attribute.AttributeResponse;
import org.example.qrproject.Module.AccountEntity;
import org.example.qrproject.Module.AttributeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttributeMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "dataType",ignore = true)
    @Mapping(target = "account",ignore = true)
    AttributeEntity toEntity(AttributeRequest request);

    //    @Mapping(target = "role",ignore = true)
    AttributeResponse toResponse(AttributeEntity attributeEntity);
}

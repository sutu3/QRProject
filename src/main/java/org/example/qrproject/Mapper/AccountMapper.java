package org.example.qrproject.Mapper;

import org.example.qrproject.Dtos.Request.Account.AccountRequest;
import org.example.qrproject.Dtos.Response.Account.AccountResponse;
import org.example.qrproject.Module.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "role",ignore = true)
    AccountEntity toEntity(AccountRequest request);

    //    @Mapping(target = "role",ignore = true)
    AccountResponse toResponse(AccountEntity accountEntity);
}

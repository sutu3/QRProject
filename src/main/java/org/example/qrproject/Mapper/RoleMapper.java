package org.example.qrproject.Mapper;

import org.example.qrproject.Dtos.Request.Role.RoleRequest;
import org.example.qrproject.Dtos.Response.Role.RoleResponse;
import org.example.qrproject.Module.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "account",ignore = true)
    RoleEntity toEntity(RoleRequest request);

    //    @Mapping(target = "role",ignore = true)
    RoleResponse toResponse(RoleEntity Entity);
}

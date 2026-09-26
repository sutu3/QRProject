package org.example.qrproject.Service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.qrproject.Dtos.Request.Role.RoleRequest;
import org.example.qrproject.Dtos.Response.Role.RoleResponse;
import org.example.qrproject.Exception.AppException;
import org.example.qrproject.Exception.ErrorCode;
import org.example.qrproject.Mapper.RoleMapper;
import org.example.qrproject.Module.RoleEntity;
import org.example.qrproject.Repo.RoleRepo;
import org.example.qrproject.Service.RoleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepo roleRepo;
    RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        RoleEntity role = roleMapper.toEntity(request);
        if (roleRepo.findByRoleName(request.getRoleName()).isPresent()) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        role.setCreatedAt(LocalDateTime.now());
        role.setIsDeleted(false);
        roleRepo.save(role);
        return roleMapper.toResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRole() {
        return roleRepo.findAll().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }
}

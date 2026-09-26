package org.example.qrproject.Service;

import org.example.qrproject.Dtos.Request.Role.RoleRequest;
import org.example.qrproject.Dtos.Response.Role.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    RoleResponse createRole(RoleRequest request);

    List<RoleResponse> getAllRole();

}

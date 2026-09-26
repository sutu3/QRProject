package org.example.qrproject.Repo;

import org.example.qrproject.Module.InvalidateTokenEntity;
import org.example.qrproject.Module.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity,String> {
    Optional<RoleEntity> findByRoleName(String roleName);
}

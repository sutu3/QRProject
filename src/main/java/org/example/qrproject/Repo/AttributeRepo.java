// AttributeRepo.java  
package org.example.qrproject.Repo;

import org.example.qrproject.Module.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeRepo extends JpaRepository<AttributeEntity, String> {
    List<AttributeEntity> findByAccount_IdAccountAndIsDeletedFalse(String accountId);
    Optional<AttributeEntity> findByIdAttributeAndAccount_IdAccountAndIsDeletedFalse(String id, String accountId);
    boolean existsByAccount_IdAccountAndCodeIgnoreCaseAndIsDeletedFalse(String accountId, String code);
}
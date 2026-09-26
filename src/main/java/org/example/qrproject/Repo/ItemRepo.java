// ItemRepo.java  
package org.example.qrproject.Repo;

import org.example.qrproject.Module.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<ItemEntity, String> {
    List<ItemEntity> findByCreatedBy_IdAccountAndIsDeletedFalse(String accountId);
    List<ItemEntity> findByCreatedBy_IdAccountAndCategory_IdCategoryAndIsDeletedFalse(String accountId, String categoryId);
    Optional<ItemEntity> findByIdItemAndCreatedBy_IdAccountAndIsDeletedFalse(String id, String accountId);
    Optional<ItemEntity> findByCodeAndIsDeletedFalse(String code);
}
// CategoryAttributeRepo.java  
package org.example.qrproject.Repo;

import org.example.qrproject.Module.CategoryAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryAttributeRepo extends JpaRepository<CategoryAttributeEntity, String> {
    List<CategoryAttributeEntity> findByCategory_IdCategoryAndIsDeletedFalseOrderBySortOrderAsc(String categoryId);
    boolean existsByCategory_IdCategoryAndAttribute_IdAttributeAndIsDeletedFalse(String categoryId, String attributeId);
    Optional<CategoryAttributeEntity> findByIdCategoryAttributeAndCategory_IdCategory(String id, String categoryId);
}
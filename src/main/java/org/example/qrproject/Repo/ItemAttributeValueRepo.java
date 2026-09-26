// ItemAttributeValueRepo.java  
package org.example.qrproject.Repo;

import org.example.qrproject.Module.ItemAttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemAttributeValueRepo extends JpaRepository<ItemAttributeValueEntity, String> {
    List<ItemAttributeValueEntity> findByItem_IdItemAndIsDeletedFalse(String itemId);
}
package org.example.qrproject.Repo;

import org.example.qrproject.Module.ItemStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemStatusHistoryRepo extends JpaRepository<ItemStatusHistoryEntity, String> {
    // xem lịch sử mới nhất trước
    List<ItemStatusHistoryEntity> findByItem_IdItemAndIsDeletedFalseOrderByChangedAtDesc(String itemId);
}
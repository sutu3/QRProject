package org.example.qrproject.Service;

import org.example.qrproject.Dtos.Request.Item.ItemRequest;
import org.example.qrproject.Dtos.Response.Item.ItemResponse;
import org.example.qrproject.Dtos.Response.Item.ItemStatusHistoryResponse;
import org.example.qrproject.Enum.ItemStatus;
import org.example.qrproject.Module.ItemEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    ItemResponse create(ItemRequest req);
    List<ItemResponse> list(String categoryId);
    ItemResponse update(String id, ItemRequest req);
    void markPrinted(String id);
    void delete(String id);
    byte[] generateQrPng(String id, int size);
    ItemResponse resolveByCode(String code);
    ItemEntity getOwned(String id);
    ItemResponse changeStatus(String id, ItemStatus newStatus, String note);
    List<ItemStatusHistoryResponse> getStatusHistory(String id);

}

package org.example.qrproject.Dtos.Response.Item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.qrproject.Enum.ItemStatus;

import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemStatusHistoryResponse {
    ItemStatus oldStatus;
    ItemStatus newStatus;
    String note;
    LocalDateTime changedAt;
    String changedBy;
}

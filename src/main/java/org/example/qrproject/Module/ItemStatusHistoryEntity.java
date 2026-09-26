package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.qrproject.Enum.ItemStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_status_histories")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemStatusHistoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_history", columnDefinition = "VARCHAR(36) COMMENT 'Id lịch sử'")
    String idHistory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    ItemEntity item;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", length = 30)
    ItemStatus oldStatus; // null nếu là bản ghi đầu tiên lúc tạo

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 30)
    ItemStatus newStatus;

    @Column(name = "note", columnDefinition = "VARCHAR(1000) COMMENT 'ghi chú'")
    String note;

    @Column(name = "changed_at", nullable = false)
    LocalDateTime changedAt;

    // người thực hiện thay đổi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by")
    AccountEntity changedBy;
}
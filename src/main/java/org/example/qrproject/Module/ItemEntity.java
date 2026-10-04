package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.qrproject.Enum.ItemStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item", columnDefinition = "VARCHAR(36) COMMENT 'Id của mục'")
    String idItem;

    // mã duy nhất ghi vào QR  
    @Column(name = "code", nullable = false, unique = true, length = 80)
    @Builder.Default
    String code = UUID.randomUUID().toString();

    @Column(name = "itemName", columnDefinition = "VARCHAR(255) COMMENT 'tên của mục'", nullable = false)
    String itemName;

    @Column(columnDefinition = "VARCHAR(1000) COMMENT 'mô tả'")
    String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_category", nullable = false)
    CategoryEntity category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    ItemStatus status = ItemStatus.ACTIVE;

    // đã in QR ra giấy chưa  
    @Column(nullable = false)
    @Builder.Default
    boolean printed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    AccountEntity createdBy;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<ItemAttributeValueEntity> values = new ArrayList<>();
}
package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.qrproject.Enum.ItemStatus;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item",columnDefinition = "VARCHAR(36) COMMENT 'Id của mục'")
    String idItem;

    @Column(name = "itemName",columnDefinition = "VARCHAR(255) COMMENT 'tên của mục'", nullable = false)
    String itemName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_category", nullable = false)
    CategoryEntity category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ItemStatus status = ItemStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private AccountEntity createdBy;


}

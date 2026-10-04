package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
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
public class QrItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_qr_item", columnDefinition = "VARCHAR(36) COMMENT 'Id mã QR'")
    String id;

    @Column(name = "code", columnDefinition = "VARCHAR(100) COMMENT 'Mã QR duy nhất'", nullable = false, unique = true)
    @Builder.Default
    String code = UUID.randomUUID().toString();

    @Column(name = "name", columnDefinition = "VARCHAR(255) COMMENT 'Tên đồ vật'", nullable = false)
    String name;

    @Column(name = "description", columnDefinition = "TEXT COMMENT 'Mô tả đồ vật'")
    String description;

    @Column(columnDefinition = "BOOL COMMENT 'Đã in mã QR hay chưa'", nullable = false)
    @Builder.Default
    boolean printed = false;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP COMMENT 'Thời gian tạo'", nullable = false, updatable = false)
    @Builder.Default
    Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account",nullable = false, foreignKey = @ForeignKey(name = "fk_qr_item_account"))
    AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_category", nullable = false, foreignKey = @ForeignKey(name = "fk_qr_item_category"))
    CategoryEntity category;

    @OneToMany(mappedBy = "qrItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<QrAttributeValue> values = new ArrayList<>();
}
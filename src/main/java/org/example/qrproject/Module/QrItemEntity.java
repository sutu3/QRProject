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
@Table(name = "qr_items")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QrItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     Long id;

    @Column(nullable = false, unique = true)
     String code = UUID.randomUUID().toString();

    @Column(nullable = false)
     String name;

     String description;

    @Column(nullable = false)
     boolean printed = false;

    @Column(nullable = false, updatable = false)
     Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account")
     AccountEntity account;

    // đồ vật bắt buộc thuộc một loại để biết bộ thuộc tính
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_category")
     CategoryEntity category;

    @OneToMany(mappedBy = "qrItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
     List<QrAttributeValue> values = new ArrayList<>();
}
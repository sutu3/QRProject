package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_category",columnDefinition = "VARCHAR(36) COMMENT 'Id của thể loại'")
    String idCategory;

    @Column(name = "categoryName",columnDefinition = "VARCHAR(255) COMMENT 'tên thể loại'", nullable = false)
    String categoryName;

    @Column(name = "description",columnDefinition = "VARCHAR(255) COMMENT 'mô tả thể loại'", nullable = false)
    String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account")
    AccountEntity account;
}

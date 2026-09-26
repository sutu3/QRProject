package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "category_attributes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "attribute_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryAttributeEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_category_attribute",columnDefinition = "VARCHAR(36) COMMENT 'Id'")
    String idCategoryAttribute;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_id", nullable = false)
     AttributeEntity attribute;

    @Column(nullable = false)
    boolean required;

    @Column(nullable = false)
    int sortOrder = 0;

    @Column(name = "defaultValue",columnDefinition = "VARCHAR(100) COMMENT 'giá trị'",length = 1000)
    String defaultValue;
}

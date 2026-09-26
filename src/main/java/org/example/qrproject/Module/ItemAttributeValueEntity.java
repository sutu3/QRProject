package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "item_attribute_values",
        uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "attribute_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemAttributeValueEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item_attribute_value",columnDefinition = "VARCHAR(36) COMMENT 'Id'")
    String idItemAttributeValue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
     ItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_id", nullable = false)
     AttributeEntity attribute;

    @Column(length = 5000)
     String value;
}

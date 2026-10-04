package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QrAttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_qr_attribute_value", columnDefinition = "VARCHAR(36) COMMENT 'Id của mã qr thuộc tính đồ vật'")
    String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "qr_item_id")
    QrItemEntity qrItem;

    // cùng thuộc tính, khác giá trị  
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_definition_id")
    AttributeEntity attributeDefinition;

    @Column(name = "attr_value", columnDefinition = "TEXT")
     String value;
}

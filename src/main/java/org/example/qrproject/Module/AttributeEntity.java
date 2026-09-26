package org.example.qrproject.Module;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.qrproject.Enum.DataType;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttributeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_item",columnDefinition = "VARCHAR(36) COMMENT 'Id của mục'")
    String idAttribute;

    @Column(name = "attribute_name",columnDefinition = "VARCHAR(100) COMMENT 'tên thuộc tính'",nullable = false)
    String attributeName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    DataType dataType = DataType.TEXT;

    @Column(name = "description",columnDefinition = "VARCHAR(256) COMMENT 'mô tả'",nullable = false)
    String description;
}

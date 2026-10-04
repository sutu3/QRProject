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
public class AttributeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_attribute", columnDefinition = "VARCHAR(36) COMMENT 'Id của thuộc tính'")
    String idAttribute;

    // key máy ghi vào payload QR, duy nhất trong từng tài khoản
    @Column(name = "code", columnDefinition = "VARCHAR(80) COMMENT 'mã thuộc tính'", nullable = false)
    String code;

    @Column(name = "attribute_name", columnDefinition = "VARCHAR(100) COMMENT 'tên thuộc tính'", nullable = false)
    String attributeName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    DataType dataType = DataType.TEXT;

    @Column(name = "description", columnDefinition = "VARCHAR(256) COMMENT 'mô tả'")
    String description;

    @Column(name = "validation_regex", length = 1000)
    String validationRegex;

    // options cho SELECT / MULTI_SELECT, JSON: ["A","B","C"]
    @Column(name = "options", columnDefinition = "TEXT")
    String options;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_account", nullable = false)
    AccountEntity account;
}
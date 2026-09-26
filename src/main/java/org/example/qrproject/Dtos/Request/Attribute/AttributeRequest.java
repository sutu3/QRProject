// Request/AttributeResponse.java
package org.example.qrproject.Dtos.Request.Attribute;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttributeRequest {
    @NotBlank
    String code;
    @NotBlank
    String attributeName;
    String dataType;     // TEXT|NUMBER|DATE|SELECT|...
    String description;
    String validationRegex;
    String options;
}// JSON array cho SELECT/MULTI_SELECT

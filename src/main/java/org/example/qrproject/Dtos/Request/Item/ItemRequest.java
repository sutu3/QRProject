package org.example.qrproject.Dtos.Request.Item;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {
    @NotBlank
    String itemName;
    String description;
    @NotBlank
    String categoryId;
    Map<String, String> attributeValues ;  // key = code thuộc tính
}

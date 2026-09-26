package org.example.qrproject.Dtos.Response.Item;

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
public class ItemResponse{
        String idItem;
        String code;
        String itemName;
        String description;
        String status;
        boolean printed;
        String categoryId;
        Map<String, String> attributeValues;
}

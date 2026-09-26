package org.example.qrproject.Dtos.Request.Category;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.example.qrproject.Module.AttributeEntity;
import org.example.qrproject.Module.CategoryEntity;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryAttributeRequest{

    String category;

    String attribute;

    boolean required;

    int sortOrder = 0;

    String defaultValue;
}

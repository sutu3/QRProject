// CategoryController.java  
package org.example.qrproject.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.qrproject.Dtos.ApiResponse;
import org.example.qrproject.Dtos.Request.Category.CategoryAttributeRequest;
import org.example.qrproject.Dtos.Request.Category.CategoryRequest;
import org.example.qrproject.Dtos.Response.Category.CategoryResponse;
import org.example.qrproject.Service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category API", description = "Loại đồ vật + bộ thuộc tính")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    public ApiResponse<CategoryResponse> create(
            @Valid
            @RequestBody CategoryRequest req) {
        return ApiResponse.<CategoryResponse>builder()
                .Result(categoryService.create(req))
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> list() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .Result(categoryService.list())
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> detail(@PathVariable String id) {
        return ApiResponse.<CategoryResponse>builder()
                .Result(categoryService.getDetail(id))
                .success(true)
                .code(0)
                .build();
    }

    // gán thuộc tính tái sử dụng vào loại  
    @PostMapping("/{id}/attributes")
    public ApiResponse<CategoryResponse> addAttribute(
            @PathVariable String id,
            @RequestBody CategoryAttributeRequest req) {
        return ApiResponse.<CategoryResponse>builder()
                .Result(categoryService.addAttribute(id, req))
                .success(true)
                .code(0)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ApiResponse.<String>builder()
                .Result("Deleted")
                .success(true)
                .code(0)
                .build();
    }
}
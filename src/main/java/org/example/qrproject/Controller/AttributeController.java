// AttributeController.java  
package org.example.qrproject.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.qrproject.Dtos.ApiResponse;
import org.example.qrproject.Dtos.Request.Attribute.AttributeRequest;
import org.example.qrproject.Dtos.Response.Attribute.AttributeResponse;
import org.example.qrproject.Service.AttributeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attributes")
@RequiredArgsConstructor
@Tag(name = "Attribute API", description = "Kho thuộc tính tái sử dụng")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttributeController {
    AttributeService attributeService;

    @PostMapping
    public ApiResponse<AttributeResponse> create(@Valid @RequestBody AttributeRequest req) {
        return ApiResponse.<AttributeResponse>builder()
                .Result(attributeService.create(req))
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping
    public ApiResponse<List<AttributeResponse>> list() {
        return ApiResponse.<List<AttributeResponse>>builder()
                .Result(attributeService.list())
                .success(true)
                .code(0)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        attributeService.delete(id);
        return ApiResponse.<String>builder()
                .Result("Deleted")
                .success(true)
                .code(0)
                .build();
    }
}
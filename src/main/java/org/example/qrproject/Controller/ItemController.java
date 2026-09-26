// ItemController.java  
package org.example.qrproject.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.qrproject.Dtos.ApiResponse;
import org.example.qrproject.Dtos.Request.Item.ItemRequest;
import org.example.qrproject.Dtos.Request.Item.ItemStatusChangeRequest;
import org.example.qrproject.Dtos.Response.Item.ItemResponse;
import org.example.qrproject.Dtos.Response.Item.ItemStatusHistoryResponse;
import org.example.qrproject.Service.ItemService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Tag(name = "Item API", description = "Đồ vật + sinh mã QR")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemController {
    ItemService itemService;

    @PostMapping
    public ApiResponse<ItemResponse> create(
            @Valid
            @RequestBody ItemRequest req) {
        return ApiResponse.<ItemResponse>builder()
                .Result(itemService.create(req))
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping
    public ApiResponse<List<ItemResponse>> list(
            @RequestParam(required = false) String categoryId) {
        return ApiResponse.<List<ItemResponse>>builder()
                .Result(itemService.list(categoryId))
                .success(true)
                .code(0)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ItemResponse> update(
            @PathVariable String id,
            @Valid @RequestBody ItemRequest req) {
        return ApiResponse.<ItemResponse>builder()
                .Result(itemService.update(id, req))
                .success(true)
                .code(0)
                .build();
    }

    // đánh dấu đã in QR  
    @PostMapping("/{id}/print")
    public ApiResponse<String> markPrinted(
            @PathVariable String id) {
        itemService.markPrinted(id);
        return ApiResponse.<String>builder()
                .Result("Printed")
                .success(true)
                .code(0)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        itemService.delete(id);
        return ApiResponse.<String>builder()
                .Result("Deleted")
                .success(true)
                .code(0)
                .build();
    }

    // sinh ảnh PNG mã QR  
    @GetMapping(value = "/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> qrImage(
            @PathVariable String id,
            @RequestParam(defaultValue = "300") int size) {
        return ResponseEntity.ok(itemService.generateQrPng(id, size));
    }

    // resolve nội dung khi client quét mã — cho phép public qua /qr/** trong SecurityConfig  
    @GetMapping("/resolve/{code}")
    public ApiResponse<ItemResponse> resolve(
            @PathVariable String code) {
        return ApiResponse.<ItemResponse>builder()
                .Result(itemService.resolveByCode(code))
                .success(true)
                .code(0)
                .build();
    }
    @PatchMapping("/{id}/status")
    public ApiResponse<ItemResponse> changeStatus(@PathVariable String id,
                                                  @Valid @RequestBody ItemStatusChangeRequest req) {
        return ApiResponse.<ItemResponse>builder()
                .Result(itemService.changeStatus(id, req.getStatus(), req.getNote()))
                .success(true)
                .code(0)
                .build();
    }

    @GetMapping("/{id}/status-history")
    public ApiResponse<List<ItemStatusHistoryResponse>> history(@PathVariable String id) {
        return ApiResponse.<List<ItemStatusHistoryResponse>>builder()
                .Result(itemService.getStatusHistory(id))
                .success(true)
                .code(0)
                .build();
    }
}
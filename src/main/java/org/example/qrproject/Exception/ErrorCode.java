package org.example.qrproject.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
public enum ErrorCode {
    // ===== Chung (9xxx / 1xxx) =====
    UNCATEGORIZED(9999, "Lỗi hệ thống không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Khóa/tham số không hợp lệ", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1002, "Tên đăng nhập hoặc mật khẩu không đúng", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1003, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),

    // ===== Account / Role (2xxx) =====
    ACCOUNT_NOT_FOUND(2001, "Không tìm thấy tài khoản", HttpStatus.NOT_FOUND),
    ACCOUNT_LOCKED(2006, "Tài khoản đã bị khóa", HttpStatus.BAD_REQUEST),
    ACCOUNT_IS_EXIST(2002, "Tài khoản đã tồn tại", HttpStatus.CONFLICT),
    PASSWORD_INVALID(2003, "Mật khẩu không hợp lệ", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND(2004, "Không tìm thấy vai trò", HttpStatus.NOT_FOUND),
    ROLE_IS_EXIST(2005, "Vai trò đã tồn tại", HttpStatus.CONFLICT),

    // ===== Attribute  / Category (3xxx) =====
    ATTRIBUTE_NOT_FOUND(3001,"Không tìm thấy thuộc tính",HttpStatus.NOT_FOUND),
    ATTRIBUTE_EXIST(3002,"Thuộc tính đã tồn tại trong thể loại",HttpStatus.CONFLICT),
    CATEGORY_NOT_FOUND(3003,"Không tìm thấy thể loại",HttpStatus.NOT_FOUND),
    ITEM_NOT_FOUND(3004, "Không tìm thấy đồ vật", HttpStatus.NOT_FOUND),
    QR_CODE_NOT_FOUND(3005, "Không tìm thấy mã QR", HttpStatus.NOT_FOUND),
    ATTRIBUTE_NOT_ALLOWED(3006, "Thuộc tính không thuộc bộ của loại đồ vật này", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_MISSING_REQUIRED(3007, "Thiếu thuộc tính bắt buộc", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_INVALID_VALUE(3008, "Giá trị thuộc tính không hợp lệ với kiểu dữ liệu", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_VALUE_NOT_IN_OPTIONS(3009, "Giá trị không nằm trong danh sách cho phép", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_NO_OPTIONS(3010, "Thuộc tính kiểu SELECT/MULTI_SELECT chưa cấu hình options", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_INVALID_OPTIONS(3011, "Cấu hình options của thuộc tính không hợp lệ", HttpStatus.INTERNAL_SERVER_ERROR),
    ATTRIBUTE_IS_EXIST(3012, "Mã thuộc tính đã tồn tại", HttpStatus.CONFLICT),

    // ===== QR (4xxx) =====
    QR_GENERATE_FAILED(4012, "Không thể tạo ảnh mã QR", HttpStatus.INTERNAL_SERVER_ERROR),


    ;

    ErrorCode(int code, String message, HttpStatusCode status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private int code;
    private String message;
    private HttpStatusCode status;
}

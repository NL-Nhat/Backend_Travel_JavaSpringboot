package com.example.travel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.travel.dto.response.ErrorResponseDTO;

/* Thay vì bạn phải viết hàng chục khối try-catch trong từng Controller,
chỉ cần viết logic xử lý lỗi tại một nơi duy nhất.

Đây là một Annotation kết hợp giữa @ControllerAdvice và @ResponseBody

Khi có một lỗi quăng ra (throw), nó sẽ được đưa về class này để xử lý
và trả về định dạng JSON thống nhất cho Client. */

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý lỗi thiếu trường dữ liệu (FieldRequiredException bạn đã tạo)
    @ExceptionHandler(FieldRequiredException.class)
    public ResponseEntity<ErrorResponseDTO> handleFieldRequiredException(FieldRequiredException e) {
        ErrorResponseDTO err = new ErrorResponseDTO();
        err.setMessage(e.getMessage());
        err.setDetail("Hãy kiểm tra các trường dữ liệu bắt buộc");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    // Xử lý lỗi không tìm thấy tài nguyên (RuntimeException). vd: không tìm thấy người dùng theo mã người dùng
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // Bắt lỗi Validation (Dữ liệu đầu vào không hợp lệ)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponseDTO> handlingValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .findFirst()
            .map(err -> err.getDefaultMessage())
            .orElse("Dữ liệu không hợp lệ");
        
        ErrorResponseDTO apiResponse = new ErrorResponseDTO();
        apiResponse.setMessage(message);
        apiResponse.setDetail("Dữ liệu đầu vào không hợp lệ");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    // Xử lý tất cả các lỗi hệ thống khác (Exception chung)
    @ExceptionHandler(Exception.class)
    private ResponseEntity<String> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
    }

}

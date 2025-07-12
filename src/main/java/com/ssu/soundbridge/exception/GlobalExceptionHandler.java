package com.ssu.soundbridge.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /** ❶ Bean Validation 실패( @Valid / @Validated ) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        // 모든 필드 오류 메시지를 뽑아 List<String> 으로 변환
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> String.format("[%s] %s",
                        err.getField(),           // 오류 필드명
                        err.getDefaultMessage())) // @NotBlank 등에서 적은 message
                .toList();

        // ❶ 여러 건일 수도 있으니 배열로 응답
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("입력값이 올바르지 않습니다.", errorMessages));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage(),null));
    }

    @Data
    @AllArgsConstructor
    static class ErrorResponse {
        private String message;
        private List<String> details;  // 필드별 상세 메시지 (nullable)
    }



}
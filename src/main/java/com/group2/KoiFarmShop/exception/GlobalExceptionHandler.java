package com.group2.KoiFarmShop.exception;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiReponse> handleRuntimeException(Exception e) {
        ApiReponse apiReponse = new ApiReponse();

        apiReponse.setCode(9999);
        apiReponse.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(apiReponse);
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiReponse> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiReponse apiReponse = new ApiReponse();

        apiReponse.setCode(errorCode.getCode());
        apiReponse.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(apiReponse);
    }
}

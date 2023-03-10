package com.soonhankwon.coffeeplzbackend.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(value = { RequestException.class })
    public ResponseEntity<ResponseDto<Object>> handleApiRequestException(RequestException e) {

        return new ResponseEntity<>(ResponseDto.fail(
                e.getHttpStatus(),
                e.getMessage()), e.getHttpStatus()
        );
    }
}

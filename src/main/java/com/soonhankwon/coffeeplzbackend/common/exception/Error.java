package com.soonhankwon.coffeeplzbackend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Error {
    private HttpStatus httpStatus;
    private String message;
}

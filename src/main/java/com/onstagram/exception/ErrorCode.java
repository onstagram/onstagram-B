package com.onstagram.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 2xx
    ITEM_NOT_FOUND(HttpStatus.NO_CONTENT, "게시물이 없습니다"),

    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "Bad Gateway");

    private final HttpStatus httpStatus;
    private final String message;
}

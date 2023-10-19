package com.onstagram.exception;

import lombok.Getter;

@Getter
//@AllArgsConstructor
public class OnstagramException extends RuntimeException {

    private int ErrorCode;
    private String ErrorMessage;

    public OnstagramException() {
        super();
    }
    public OnstagramException(int ErrorCode, String ErrorMessage) {
        this.ErrorCode = ErrorCode;
        this.ErrorMessage = ErrorMessage;
    }
}

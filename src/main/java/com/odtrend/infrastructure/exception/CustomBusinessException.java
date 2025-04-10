package com.odtrend.infrastructure.exception;

import lombok.Getter;

@Getter
public class CustomBusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomBusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

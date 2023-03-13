package com.monalisa.global.error.exception;

import com.monalisa.global.error.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class TooManyExcessException extends RuntimeException {
    private ErrorCode errorCode;

    public TooManyExcessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}

package com.monalisa.global.config.security.jwt;

import com.monalisa.global.error.errorcode.ErrorCode;
import com.monalisa.global.error.exception.InvalidValueException;

public class InvalidRefreshTokenException extends InvalidValueException {

    public InvalidRefreshTokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public InvalidRefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}

package com.monalisa.global.error.exception;

import com.monalisa.global.error.errorcode.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

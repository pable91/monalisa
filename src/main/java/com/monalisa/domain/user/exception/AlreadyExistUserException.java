package com.monalisa.domain.user.exception;

import com.monalisa.global.error.errorcode.ErrorCode;
import com.monalisa.global.error.exception.InvalidValueException;

public class AlreadyExistUserException extends InvalidValueException {
    public AlreadyExistUserException(ErrorCode errorCode, String accountId) {
        super(errorCode, "[account id "+ accountId + "] " + errorCode.getMessage());
    }
}

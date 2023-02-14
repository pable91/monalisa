package com.monalisa.domain.member.exception;

import com.monalisa.global.error.errorcode.ErrorCode;
import com.monalisa.global.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long userId, ErrorCode errorCode) {
        super(errorCode, "[user id "+ userId + "] " + errorCode.getMessage());
    }
}

package com.monalisa.domain.user.exception.error;

import com.monalisa.global.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(400, "U001", "User Not Found!");

    private final int status;
    private final String code;
    private final String message;
}
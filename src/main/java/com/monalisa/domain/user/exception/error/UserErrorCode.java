package com.monalisa.domain.user.exception.error;

import com.monalisa.global.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND(400, "U001", "User Not Found!"),
    ALREADY_EXIST_USER(400, "U002", "Already User Exist"),
    WRONG_PASSWORD(400, "U003", "Wrong Password");

    private final int status;
    private final String code;
    private final String message;
}

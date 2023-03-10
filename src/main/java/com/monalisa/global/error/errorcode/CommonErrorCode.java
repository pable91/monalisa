package com.monalisa.global.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Method Not Allowed"),
    HANDLE_ACCESS_DENIED(403, "C003", "Access is Denied"),

    TOO_MANY_EXCESS(400, "C004", "Too Many Access Users, try again later");

    private final int status;
    private final String code;
    private final String message;
}

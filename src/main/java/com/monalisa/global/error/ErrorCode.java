package com.monalisa.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // global
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Method Not Allowed"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // user
    USER_NOT_FOUND(400, "U001", "User Not Found!"),

    // book
    BOOK_NOT_FOUND(400, "B001", "Book Not Found!"),
    BOOK_ALREADY_REGISTER(400, "B002", "Book Already Register!"),
    IS_NOT_MY_BOOK(400, "B003", "Is Not My Book!");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}

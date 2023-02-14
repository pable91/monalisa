package com.monalisa.domain.book.exception.error;

import com.monalisa.global.error.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookErrorCode implements ErrorCode {

    BOOK_NOT_FOUND(400, "B001", "Book Not Found!"),
    BOOK_ALREADY_REGISTER(400, "B002", "Book Already Register!"),
    IS_NOT_MY_BOOK(400, "B003", "Is Not My Book!");

    private final int status;
    private final String code;
    private final String message;
}

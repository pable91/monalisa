package com.monalisa.domain.book.exception;

import com.monalisa.global.error.ErrorCode;
import com.monalisa.global.error.exception.InvalidValueException;

public class IsNotMyBookException extends InvalidValueException {
    public IsNotMyBookException(ErrorCode errorCode, Long bookId) {
        super(errorCode, "[book id "+ bookId + "] " + errorCode.getMessage());
    }
}

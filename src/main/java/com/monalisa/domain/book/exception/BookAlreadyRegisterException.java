package com.monalisa.domain.book.exception;

import com.monalisa.global.error.ErrorCode;
import com.monalisa.global.error.exception.InvalidValueException;

public class BookAlreadyRegisterException extends InvalidValueException {
    public BookAlreadyRegisterException(ErrorCode errorCode, String bookName) {
        super(errorCode, "[book name " + bookName + "] " + errorCode.getMessage());
    }
}

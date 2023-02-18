package com.monalisa.domain.book.exception;

import com.monalisa.global.error.errorcode.ErrorCode;
import com.monalisa.global.error.exception.InvalidValueException;

public class BuyNotMyBookException extends InvalidValueException {

    public BuyNotMyBookException(ErrorCode errorCode, Long bookId) {
        super(errorCode, "[book id "+ bookId + "] " + errorCode.getMessage());
    }
}
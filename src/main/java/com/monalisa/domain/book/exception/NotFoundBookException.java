package com.monalisa.domain.book.exception;

import com.monalisa.global.error.errorcode.ErrorCode;
import com.monalisa.global.error.exception.EntityNotFoundException;

public class NotFoundBookException extends EntityNotFoundException {

    public NotFoundBookException(ErrorCode errorCode, Long bookId) {
        super(errorCode, "[book id " + bookId + "] " + errorCode.getMessage());
    }

    public NotFoundBookException(ErrorCode errorCode, String bookName) {
        super(errorCode, "[book name " + bookName + "] " + errorCode.getMessage());
    }

    public NotFoundBookException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }
}

package com.monalisa.domain.book.exception;

import com.monalisa.global.error.ErrorCode;
import com.monalisa.global.error.exception.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(ErrorCode errorCode, Long bookId) {
        super(errorCode, "[book id "+ bookId + "] " + errorCode.getMessage());
    }
}

package com.monalisa.domain.book.service.facade;

import com.monalisa.domain.book.dto.response.BookResponseDto;

public interface LikeBookFacade {

    BookResponseDto likeBook(final Long bookId) throws InterruptedException;
}

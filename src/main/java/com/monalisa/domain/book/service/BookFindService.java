package com.monalisa.domain.book.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.service.queryService.BookFindQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookFindService {

    private final BookFindQueryService bookFindQueryService;

    public BookResponseDto findById(final Long bookId) {
        Book findBook = bookFindQueryService.findById(bookId);

        return BookResponseDto.of(findBook);
    }
}

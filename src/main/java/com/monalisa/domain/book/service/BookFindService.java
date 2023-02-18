package com.monalisa.domain.book.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.exception.error.BookErrorCode;
import com.monalisa.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookFindService {

    private final BookRepository bookRepository;

    public BookResponseDto findById(final Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if(!book.isPresent()) {
           throw new NotFoundBookException(BookErrorCode.BOOK_NOT_FOUND, bookId);
        }

        Book findBook = book.get();
        return BookResponseDto.of(findBook);
    }
}

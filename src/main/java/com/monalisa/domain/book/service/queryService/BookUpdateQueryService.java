package com.monalisa.domain.book.service.queryService;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookUpdateQueryService {

    private final BookRepository bookRepository;

    public Book save(final Book book) {
        return bookRepository.save(book);
    }

    public void delete(final Book book) {
        bookRepository.delete(book);
    }
}

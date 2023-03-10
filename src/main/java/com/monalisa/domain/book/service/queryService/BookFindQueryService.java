package com.monalisa.domain.book.service.queryService;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.exception.error.BookErrorCode;
import com.monalisa.domain.book.repository.BookRepository;
import com.monalisa.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookFindQueryService {

    private final BookRepository bookRepository;

    public boolean isExist(final BookRequestDto.Add addBookRequestDto, final User findUser) {
        return bookRepository.existsByNameAndUser(addBookRequestDto.getName(), findUser);
    }

    public Book findById(final Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> {
            throw new NotFoundBookException(BookErrorCode.BOOK_NOT_FOUND, bookId);
        });
    }

    public List<Book> findAllByIds(final List<Long> bookIds) {
        return bookRepository.findAllByIds(bookIds);
    }

//    public Book findByIdWithPessimisticLock(final Long bookId) {
//        return bookRepository.findByIdWithPessimisticLock(bookId).orElseThrow(() -> {
//            throw new NotFoundBookException(BookErrorCode.BOOK_NOT_FOUND, bookId);
//        });
//    }

//    public Book findByIdWithOptimisticLock(final Long bookId) {
//        return bookRepository.findByIdWithOptimisticLock(bookId).orElseThrow(() -> {
//            throw new NotFoundBookException(BookErrorCode.BOOK_NOT_FOUND, bookId);
//        });
//    }
}

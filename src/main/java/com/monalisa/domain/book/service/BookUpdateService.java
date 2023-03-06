package com.monalisa.domain.book.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.exception.BookAlreadyRegisterException;
import com.monalisa.domain.book.exception.IsNotMyBookException;
import com.monalisa.domain.book.exception.error.BookErrorCode;
import com.monalisa.domain.book.service.queryService.BookFindQueryService;
import com.monalisa.domain.book.service.queryService.BookUpdateQueryService;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookUpdateService {

    private final UserFindQueryService userFindQueryService;
    private final BookFindQueryService bookFindQueryService;
    private final BookUpdateQueryService bookUpdateQueryService;

    public BookResponseDto registerBook(final BookRequestDto.Add addBookRequestDto, final User user) {
        final User findUser = validate(addBookRequestDto, user);

        final Book newBook = Book.registerBook(addBookRequestDto, findUser);

        return BookResponseDto.of(bookUpdateQueryService.save(newBook));
    }

    private User validate(final BookRequestDto.Add addBookRequestDto, final User user) {
        final User findUser = userFindQueryService.findById(user.getId());

        if (bookFindQueryService.isExist(addBookRequestDto, findUser)) {
            throw new BookAlreadyRegisterException(BookErrorCode.BOOK_ALREADY_REGISTER, addBookRequestDto.getName());
        }
        return findUser;
    }

    public BookResponseDto updateBook(final BookRequestDto.Update updateBookRequestDto, final User user) {
        final Book findBook = bookFindQueryService.findById(updateBookRequestDto.getBookId());
        if (!findBook.isMine(user.getId())) {
            throw new IsNotMyBookException(BookErrorCode.IS_NOT_MY_BOOK, updateBookRequestDto.getBookId());
        }

        findBook.update(updateBookRequestDto);

        return BookResponseDto.of(findBook);
    }

    public BookResponseDto deleteBook(final Long bookId) {
        final Book findBook = bookFindQueryService.findById(bookId);

        bookUpdateQueryService.delete(findBook);

        return BookResponseDto.of(findBook);
    }

    public BookResponseDto likeBook(final Long bookId) {
        final Book findBook = bookFindQueryService.findById(bookId);

        findBook.addLike();

        return BookResponseDto.of(findBook);
    }

}

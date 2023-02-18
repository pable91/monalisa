package com.monalisa.domain.book.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.exception.BookAlreadyRegisterException;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.exception.IsNotMyBookException;
import com.monalisa.domain.book.exception.error.BookErrorCode;
import com.monalisa.domain.book.repository.BookRepository;
import com.monalisa.domain.user.repository.UserRepository;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.exception.UserNotFoundException;
import com.monalisa.domain.user.exception.error.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookUpdateService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookResponseDto registerBook(final BookRequestDto.Add addBookRequestDto) {
        final User findUser = validate(addBookRequestDto);

        final Book newBook = Book.registerBook(addBookRequestDto, findUser);

        return BookResponseDto.of(bookRepository.save(newBook));
    }

    private User validate(final BookRequestDto.Add addBookRequestDto) {
        // TODO
        // 나중에 로그인 기능이 있다면 굳이 필요할까?
        Optional<User> user = userRepository.findById(addBookRequestDto.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException(addBookRequestDto.getUserId(), UserErrorCode.USER_NOT_FOUND);
        }

        final User findUser = user.get();

        Optional<Book> findBook = bookRepository.findByNameAndUser(addBookRequestDto.getName(), findUser);
        if (findBook.isPresent()) {
            throw new BookAlreadyRegisterException(BookErrorCode.BOOK_ALREADY_REGISTER, addBookRequestDto.getName());
        }

        return findUser;
    }

    public BookResponseDto updateBook(final BookRequestDto.Update updateBookRequestDto) {
        Optional<Book> book = notFoundBookValidate(updateBookRequestDto.getBookId());

        Book findBook = book.get();
        if (!findBook.isMine(updateBookRequestDto.getUserId())) {
            throw new IsNotMyBookException(BookErrorCode.IS_NOT_MY_BOOK, updateBookRequestDto.getBookId());
        }

        findBook.update(updateBookRequestDto);

        return BookResponseDto.of(findBook);
    }

    public BookResponseDto deleteBook(final Long bookId) {
        Optional<Book> book = notFoundBookValidate(bookId);

        final Book findBook = book.get();
        bookRepository.delete(findBook);

        return BookResponseDto.of(findBook);
    }

    private Optional<Book> notFoundBookValidate(final Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            throw new NotFoundBookException(BookErrorCode.BOOK_NOT_FOUND, bookId);
        }
        return book;
    }
}

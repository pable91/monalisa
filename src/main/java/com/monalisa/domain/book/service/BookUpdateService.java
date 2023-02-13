package com.monalisa.domain.book.service;

import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.exception.BookAlreadyRegisterException;
import com.monalisa.domain.book.exception.BookNotFoundException;
import com.monalisa.domain.book.exception.IsNotMyBookException;
import com.monalisa.domain.member.exception.UserNotFoundException;
import com.monalisa.domain.book.repository.BookRepository;
import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.repository.UserRepository;
import com.monalisa.domain.member.domain.User;
import com.monalisa.global.error.ErrorCode;
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

    public Book addBookService(final BookRequestDto.Add addBookRequestDto) {
        User findUser = validate(addBookRequestDto);

        Book newBook = Book.of(addBookRequestDto, findUser);

        return bookRepository.save(newBook);
    }

    private User validate(final BookRequestDto.Add addBookRequestDto) {
        Optional<User> user = userRepository.findById(addBookRequestDto.getUserId());

        if(!user.isPresent()) {
            throw new UserNotFoundException(addBookRequestDto.getUserId(), ErrorCode.USER_NOT_FOUND);
        }

        User findUser = user.get();

        Optional<Book> findBook = bookRepository.findByNameAndUser(addBookRequestDto.getName(), findUser);
        if(findBook.isPresent()) {
            throw new BookAlreadyRegisterException(ErrorCode.BOOK_ALREADY_REGISTER, addBookRequestDto.getName());
        }

        return findUser;
    }

    public Book updateBookService(final BookRequestDto.Update updateBookRequestDto) {
        Optional<Book> book = bookRepository.findById(updateBookRequestDto.getBookId());
        if(!book.isPresent()) {
            throw new BookNotFoundException(ErrorCode.BOOK_NOT_FOUND, updateBookRequestDto.getBookId());
        }

        Book findBook = book.get();
        if(!findBook.isMine(updateBookRequestDto.getUserId())) {
            throw new IsNotMyBookException(ErrorCode.IS_NOT_MY_BOOK, updateBookRequestDto.getBookId());
        }

        findBook.update(updateBookRequestDto);

        return findBook;
    }
}

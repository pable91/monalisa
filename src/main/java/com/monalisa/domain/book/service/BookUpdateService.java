package com.monalisa.domain.book.service;

import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.repository.BookRepository;
import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.repository.UserRepository;
import com.monalisa.domain.member.domain.User;
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
            throw new RuntimeException("사용자가 존재하지않음");
        }

        User findUser = user.get();

        Optional<Book> findBook = bookRepository.findByNameAndUser(addBookRequestDto.getName(), findUser);
        if(findBook.isPresent()) {
            throw new RuntimeException("해당 사용자가 이미 책을 판매 등록했습니다");
        }

        return findUser;
    }

    public Book updateBookService(final BookRequestDto.Update updateBookRequestDto) {
        Optional<Book> book = bookRepository.findById(updateBookRequestDto.getBookId());
        if(!book.isPresent()) {
            throw new RuntimeException("수정할 책이 존재하지 않습니다");
        }

        Book findBook = book.get();
        if(!findBook.isMine(updateBookRequestDto.getUserId())) {
            throw new RuntimeException("내가 판매 등록한 책이 아닙니다");
        }

        findBook.update(updateBookRequestDto);

        return findBook;
    }
}

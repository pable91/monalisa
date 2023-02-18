package com.monalisa.domain.book.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.exception.BookAlreadyRegisterException;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.exception.IsNotMyBookException;
import com.monalisa.domain.book.repository.BookRepository;
import com.monalisa.domain.user.repository.UserRepository;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookUpdateServiceTest {

    @InjectMocks
    private BookUpdateService bookUpdateService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private BookRequestDto.Add addBookRequestDto;
    private BookRequestDto.Update updateBookRequestDto;

    @BeforeEach
    public void init() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("kim")
                .build();
        user = User.from(1L, userRequestDto.getName());

        addBookRequestDto = BookRequestDto.Add.builder()
                .name("kim")
                .desc("desc")
                .cost(1000)
                .author("author")
                .userId(1L)
                .build();

        updateBookRequestDto = BookRequestDto.Update.builder()
                .userId(1L)
                .bookId(1L)
                .name("update name")
                .desc("update desc")
                .cost(10000000)
                .author("update author")
                .build();
    }

    @Test
    @DisplayName("판매할 책 등록  테스트")
    public void addBookTest() {
        // give
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        when(bookRepository.save(any(Book.class))).thenReturn(Book.registerBook(addBookRequestDto, user));
//        doReturn(Book.of(addBookRequestDto, newUser)).when(bookRepository)
//                .save(any(Book.class));

        // when
        BookResponseDto bookResponseDto = bookUpdateService.registerBook(addBookRequestDto);

        // then
        Assertions.assertThat(bookResponseDto.getName()).isEqualTo("kim");
        Assertions.assertThat(bookResponseDto.getDesc()).isEqualTo("desc");
        Assertions.assertThat(bookResponseDto.getCost()).isEqualTo(1000);
        Assertions.assertThat(bookResponseDto.getAuthor()).isEqualTo("author");
    }

    @Test
    @DisplayName("판매 책을 등록할때 유저가 없으면 예외를 던진다")
    public void userNotFoundExceptionTest() {
        // give
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, () -> {
            bookUpdateService.registerBook(addBookRequestDto);
        });
    }

    @Test
    @DisplayName("판매 책을 등록할때 이미 등록된 책이면 예외를 던진다")
    public void bookAlreadyRegisterExceptionTest() {
        // give
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        Book book = Book.registerBook(addBookRequestDto, user);

        when(bookRepository.findByNameAndUser(any(), any())).thenReturn(Optional.of(book));

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(BookAlreadyRegisterException.class, () -> {
            bookUpdateService.registerBook(addBookRequestDto);
        });
    }

    @Test
    @DisplayName("판매 등록 책 업데이트 테스트")
    public void updateBookTest() {
        // give
        Book book = Book.registerBook(addBookRequestDto, user);

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        // when
        BookResponseDto bookResponseDto = bookUpdateService.updateBook(updateBookRequestDto);

        // then
        Assertions.assertThat(bookResponseDto.getName()).isEqualTo("update name");
        Assertions.assertThat(bookResponseDto.getDesc()).isEqualTo("update desc");
        Assertions.assertThat(bookResponseDto.getCost()).isEqualTo(10000000);
        Assertions.assertThat(bookResponseDto.getAuthor()).isEqualTo("update author");
    }


    @Test
    @DisplayName("책 수정하려고 할때 책이 존재하지 않으면 예외를 던진다")
    public void bookNotFoundExceptionTest() {
        // give
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundBookException.class, () -> {
            bookUpdateService.updateBook(updateBookRequestDto);
        });
    }

    @Test
    @DisplayName("수정하려는 책이 내 책이 아니면 예외를 던진다")
    public void bookIsNotMyBook() {
        // give
        Book book = Book.registerBook(addBookRequestDto, user);

        BookRequestDto.Update notEqualUserIdUpdateDto = BookRequestDto.Update.builder()
                .userId(2L)
                .bookId(1L)
                .name("update name")
                .desc("update desc")
                .cost(10000000)
                .author("update author")
                .build();

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(IsNotMyBookException.class, () -> {
            bookUpdateService.updateBook(notEqualUserIdUpdateDto);
        });
    }

    @Test
    @DisplayName("판매 등록된 책을 삭제하는 테스트")
    public void deleteBookTest() {
        // give
        Book book = Book.registerBook(addBookRequestDto, user);

        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        // when
        BookResponseDto bookResponseDto = bookUpdateService.deleteBook(book.getId());

        // then
        Assertions.assertThat(bookResponseDto.getName()).isEqualTo("kim");
        Assertions.assertThat(bookResponseDto.getDesc()).isEqualTo("desc");
        Assertions.assertThat(bookResponseDto.getCost()).isEqualTo(1000);
        Assertions.assertThat(bookResponseDto.getAuthor()).isEqualTo("author");
    }

    @Test
    @DisplayName("등록된 책을 삭째할때 해당 책은 존재하지않으면 예외를 던진다")
    public void bookNotFoundExceptionTestByDelete() {
        // give
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundBookException.class, () -> {
            bookUpdateService.deleteBook(1L);
        });
    }
}
package com.monalisa.domain.book.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.exception.BookAlreadyRegisterException;
import com.monalisa.domain.book.exception.IsNotMyBookException;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.service.queryService.BookFindQueryService;
import com.monalisa.domain.book.service.queryService.BookUpdateQueryService;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.exception.UserNotFoundException;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookUpdateServiceTest {

    @InjectMocks
    private BookUpdateService bookUpdateService;

    @Mock
    private UserFindQueryService userFindQueryService;

    @Mock
    private BookFindQueryService bookFindQueryService;

    @Mock
    private BookUpdateQueryService bookUpdateQueryService;

    private User user;
    private BookRequestDto.Add addBookRequestDto;
    private BookRequestDto.Update updateBookRequestDto;

    @BeforeEach
    public void init() {
        user = User.createTestUser(1L, "kim", "kim@naver.com");

        addBookRequestDto = BookRequestDto.Add.builder()
                .name("kim")
                .desc("desc")
                .cost(1000)
                .author("author")
                .build();

        updateBookRequestDto = BookRequestDto.Update.builder()
                .bookId(1L)
                .name("update name")
                .desc("update desc")
                .cost(10000000)
                .author("update author")
                .build();
    }

    @Test
    @DisplayName("????????? ??? ?????? ?????????")
    public void addBookTest() {
        // give
        when(userFindQueryService.findById(any())).thenReturn(user);

        when(bookUpdateQueryService.save(any(Book.class))).thenReturn(Book.registerBook(addBookRequestDto, user));
//        doReturn(Book.of(addBookRequestDto, newUser)).when(bookRepository)
//                .save(any(Book.class));

        // when
        BookResponseDto bookResponseDto = bookUpdateService.registerBook(addBookRequestDto, user);

        // then
        Assertions.assertThat(bookResponseDto.getName()).isEqualTo("kim");
        Assertions.assertThat(bookResponseDto.getDesc()).isEqualTo("desc");
        Assertions.assertThat(bookResponseDto.getCost()).isEqualTo(1000);
        Assertions.assertThat(bookResponseDto.getAuthor()).isEqualTo("author");
    }

    @Test
    @DisplayName("?????? ?????? ???????????? ????????? ????????? ????????? ?????????")
    public void userNotFoundExceptionTest() {
        // give
        when(userFindQueryService.findById(any())).thenThrow(UserNotFoundException.class);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, () -> {
            bookUpdateService.registerBook(addBookRequestDto, user);
        });
    }

    @Test
    @DisplayName("?????? ?????? ???????????? ?????? ????????? ????????? ????????? ?????????")
    public void bookAlreadyRegisterExceptionTest() {
        // give
        when(userFindQueryService.findById(any())).thenReturn(user);

        Book book = Book.registerBook(addBookRequestDto, user);

        when(bookFindQueryService.isExist(any(), any())).thenReturn(true);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(BookAlreadyRegisterException.class, () -> {
            bookUpdateService.registerBook(addBookRequestDto, user);
        });
    }

    @Test
    @DisplayName("?????? ?????? ??? ???????????? ?????????")
    public void updateBookTest() {
        // give
        Book book = Book.registerBook(addBookRequestDto, user);

        when(bookFindQueryService.findById(any())).thenReturn(book);

        // when
        BookResponseDto bookResponseDto = bookUpdateService.updateBook(updateBookRequestDto, user);

        // then
        Assertions.assertThat(bookResponseDto.getName()).isEqualTo("update name");
        Assertions.assertThat(bookResponseDto.getDesc()).isEqualTo("update desc");
        Assertions.assertThat(bookResponseDto.getCost()).isEqualTo(10000000);
        Assertions.assertThat(bookResponseDto.getAuthor()).isEqualTo("update author");
    }


    @Test
    @DisplayName("??? ??????????????? ?????? ?????? ???????????? ????????? ????????? ?????????")
    public void bookNotFoundExceptionTest() {
        // give
        when(bookFindQueryService.findById(any())).thenThrow(NotFoundBookException.class);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundBookException.class, () -> {
            bookUpdateService.updateBook(updateBookRequestDto, user);
        });
    }

    @Test
    @DisplayName("??????????????? ?????? ??? ?????? ????????? ????????? ?????????")
    public void bookIsNotMyBook() {
        // give
        Book book = Book.registerBook(addBookRequestDto, user);

        BookRequestDto.Update notEqualUserIdUpdateDto = BookRequestDto.Update.builder()
                .bookId(1L)
                .name("update name")
                .desc("update desc")
                .cost(10000000)
                .author("update author")
                .build();

        User anotherUser = User.createTestUser(5L, "lee", "lee@naver.com");

        when(bookFindQueryService.findById(any())).thenReturn(book);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(IsNotMyBookException.class, () -> {
            bookUpdateService.updateBook(notEqualUserIdUpdateDto, anotherUser);
        });
    }

    @Test
    @DisplayName("?????? ????????? ?????? ???????????? ?????????")
    public void deleteBookTest() {
        // give
        Book book = Book.registerBook(addBookRequestDto, user);

        when(bookFindQueryService.findById(book.getId())).thenReturn(book);

        // when
        BookResponseDto bookResponseDto = bookUpdateService.deleteBook(book.getId());

        // then
        Assertions.assertThat(bookResponseDto.getName()).isEqualTo("kim");
        Assertions.assertThat(bookResponseDto.getDesc()).isEqualTo("desc");
        Assertions.assertThat(bookResponseDto.getCost()).isEqualTo(1000);
        Assertions.assertThat(bookResponseDto.getAuthor()).isEqualTo("author");
    }

    @Test
    @DisplayName("????????? ?????? ???????????? ?????? ?????? ????????????????????? ????????? ?????????")
    public void bookNotFoundExceptionTestByDelete() {
        // give
        when(bookFindQueryService.findById(any())).thenThrow(NotFoundBookException.class);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundBookException.class, () -> {
            bookUpdateService.deleteBook(1L);
        });
    }
}
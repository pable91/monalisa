package com.monalisa.domain.book.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.service.queryService.BookFindQueryService;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
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
public class BookFindServiceTest {

    @InjectMocks
    private BookFindService bookFindService;

    @Mock
    private BookFindQueryService bookFindQueryService;

    private User user;
    private BookRequestDto.Add addBookRequestDto;

    @BeforeEach
    public void init() {
        user = User.createTestUser(1L, "kim");

        addBookRequestDto = BookRequestDto.Add.builder()
                .name("kim")
                .desc("desc")
                .cost(1000)
                .author("author")
                .userId(1L)
                .build();
    }

    @Test
    @DisplayName("등록된 책을 조회하는 테스트")
    public void findBookTest() {
        // give
        Book book = Book.registerBook(addBookRequestDto, user);
        when(bookFindQueryService.findById(any())).thenReturn(book);

        // when
        BookResponseDto bookResponseDto = bookFindService.findById(1L);

        // then
        Assertions.assertThat(bookResponseDto.getName()).isEqualTo("kim");
        Assertions.assertThat(bookResponseDto.getDesc()).isEqualTo("desc");
        Assertions.assertThat(bookResponseDto.getCost()).isEqualTo(1000);
        Assertions.assertThat(bookResponseDto.getAuthor()).isEqualTo("author");
    }

    @Test
    @DisplayName("등록된 책이 존재하지 않으면 예외를 던진다")
    public void notFoundBookExceptionTest() {
        // give
        when(bookFindQueryService.findById(any())).thenThrow(NotFoundBookException.class);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundBookException.class, () -> {
            bookFindQueryService.findById(1L);
        });
    }
}

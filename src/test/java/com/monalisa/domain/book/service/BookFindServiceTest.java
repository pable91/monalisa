package com.monalisa.domain.book.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.exception.BookNotFoundException;
import com.monalisa.domain.book.repository.BookRepository;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookFindServiceTest {

    @InjectMocks
    private BookFindService bookFindService;

    @Mock
    private BookRepository bookRepository;

    private User user;
    private BookRequestDto.Add addBookRequestDto;

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
    }


    @Test
    @DisplayName("등록된 책을 조회하는 테스트")
    public void findBookTest() {
        // give
        Book book = Book.registerBook(addBookRequestDto, user);
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

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
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(BookNotFoundException.class, () -> {
            bookFindService.findById(1L);
        });
    }
}

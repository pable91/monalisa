package com.monalisa.domain.book.service;

import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.repository.BookRepository;
import com.monalisa.domain.book.service.queryService.BookFindQueryService;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
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
public class BookFindQueryServiceTest {

    @InjectMocks
    private BookFindQueryService bookFindQueryService;

    @Mock
    private BookRepository bookRepository;

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
    @DisplayName("findById 에서 찾는 책이 안나오면 예외를 던진다")
    public void findByIdExceptionTest() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundBookException.class, () -> {
            bookFindQueryService.findById(1312213L);
        });
    }
}

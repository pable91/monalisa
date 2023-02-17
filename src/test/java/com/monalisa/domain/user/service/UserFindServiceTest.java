package com.monalisa.domain.user.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.repository.UserRepository;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.dto.UserResponseDto;
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
public class UserFindServiceTest {

    @InjectMocks
    private UserFindService userFindService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init() {
        final UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("kim")
                .build();
        user = User.from(1L, userRequestDto.getName());

        BookRequestDto.Add addBookRequestDto = BookRequestDto.Add.builder()
                .name("book1")
                .desc("desc")
                .cost(1000)
                .author("author")
                .userId(1L)
                .build();

        Book book = Book.registerBook(addBookRequestDto, user);
    }

    @Test
    @DisplayName("유저 정보 응답 테스트")
    public void profile() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        UserResponseDto responseDto = userFindService.profile(1L);

        Assertions.assertThat(responseDto.getUserName()).isEqualTo("kim");
        Assertions.assertThat(responseDto.getBookList().size()).isEqualTo(1);
        Assertions.assertThat(responseDto.getBookList().get(0).getName()).isEqualTo("book1");
    }

    @Test
    @DisplayName("유저 정보가 없으면 예외를 던진다")
    public void notUserExceptionTest() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, ()->{
            userFindService.profile(1L);
        });
    }
}

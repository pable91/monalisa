package com.monalisa.domain.user.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.user.domain.Role;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.dto.response.UserResponseDto;
import com.monalisa.domain.user.exception.AlreadyExistUserException;
import com.monalisa.domain.user.exception.UserNotFoundException;
import com.monalisa.domain.user.exception.WrongPasswordException;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import com.monalisa.domain.user.service.queryService.UserUpdateQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserFindQueryService userFindQueryService;

    @Mock
    private UserUpdateQueryService userUpdateQueryService;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User user;

    @BeforeEach
    public void init() {
        user = User.createTestUser(1L, "kim", "kim@naver.com");

        BookRequestDto.Add addBookRequestDto = BookRequestDto.Add.builder()
                .name("book1")
                .desc("desc")
                .cost(1000)
                .author("author")
                .userId(1L)
                .build();

        Book book = Book.registerBook(addBookRequestDto, user);

        //////////////////

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, "", null));
    }

    @Test
    @DisplayName("유저 정보 응답 테스트")
    public void profile() {
        when(userFindQueryService.findById(any())).thenReturn(user);

        UserResponseDto.Profile responseDto = userService.profile();

        Assertions.assertThat(responseDto.getUserName()).isEqualTo("kim");
        Assertions.assertThat(responseDto.getEmail()).isEqualTo("kim@naver.com");
        Assertions.assertThat(responseDto.getRole()).isEqualTo(Role.NORMAL);
        Assertions.assertThat(responseDto.getRegisterBookList().size()).isEqualTo(1);
        Assertions.assertThat(responseDto.getRegisterBookList().get(0).getName()).isEqualTo("book1");
    }

    @Test
    @DisplayName("유저 정보가 없으면 예외를 던진다")
    public void notUserExceptionTest() {
        when(userFindQueryService.findById(any())).thenThrow(UserNotFoundException.class);

        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, ()->{
            userService.profile();
        });
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void signupTest() {
        // give
        when(userFindQueryService.existByAccountId(any())).thenReturn(false);

        UserRequestDto.SignUp signUpUserDto =
                UserRequestDto.SignUp.builder()
                .accountId("kkk")
                .pw("12345")
                .name("kim")
                .build();

        String pw = passwordEncoder.encode("12345");
        User createUser = User.createUser("kkk", pw, "kim", "kim@naver.com");

        when(userUpdateQueryService.save(any())).thenReturn(createUser);

        // when
        UserResponseDto.SignUp responseDto = userService.signup(signUpUserDto);

        // then
        Assertions.assertThat(responseDto.getAccountId()).isEqualTo("kkk");
        Assertions.assertThat(responseDto.getName()).isEqualTo("kim");
        Assertions.assertThat(responseDto.getEmail()).isEqualTo("kim@naver.com");
    }

    @Test
    @DisplayName("회원가입할때 기존 회원이 존재하면 예외를 던진다")
    public void alreadyExistUserException() {
        // give
        when(userFindQueryService.existByAccountId(any())).thenThrow(AlreadyExistUserException.class);

        UserRequestDto.SignUp signUpUserDto =
                UserRequestDto.SignUp.builder()
                        .accountId("kkk")
                        .pw("12345")
                        .name("kim")
                        .build();

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(AlreadyExistUserException.class, ()->{
            userService.signup(signUpUserDto);
        });
    }

    @Test
    @DisplayName("로그인 테스트")
    public void loginTest() {
        // give
        String pw = passwordEncoder.encode("12345");
        User user = User.createUser("kkk", pw, "kim", "kim@naver.com");

        when(userFindQueryService.findByAccountID(any())).thenReturn(user);

        UserRequestDto.Login loginDto =
                UserRequestDto.Login.builder()
                .accountId("kkk")
                .pw("12345")
                .build();

        // when
        User loginUser = userService.login(loginDto);

        // then
        Assertions.assertThat(loginUser.getAccountID()).isEqualTo("kkk");
        Assertions.assertThat(loginUser.getPw()).isEqualTo(pw);
    }

    @Test
    @DisplayName("비밀번호가 잘못되면 예외를 던진다")
    public void wrongPasswordExceptionTest() {
        // give
        String pw1 = passwordEncoder.encode("123");
        User user = User.createUser("kkk", pw1, "kim", "kim@naver.com");

        when(userFindQueryService.findByAccountID(any())).thenReturn(user);

        UserRequestDto.Login loginDto =
                UserRequestDto.Login.builder()
                        .accountId("kkk")
                        .pw("321")
                        .build();

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(WrongPasswordException.class, ()->{
            userService.login(loginDto);
        });
    }
}

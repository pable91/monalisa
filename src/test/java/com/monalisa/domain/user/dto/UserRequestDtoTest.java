package com.monalisa.domain.user.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Stream;

class UserRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void before() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    static Stream<Arguments> signUpDtoArguments1() {
        return Stream.of(
                Arguments.arguments("id1", "12345", "kim", "kim@naver.com", 0),
                Arguments.arguments("", "12345", "", "kim@naver.com", 2),
                Arguments.arguments("id1", "", "kim", "kim@naver.com", 1),
                Arguments.arguments(null, null, null, null, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("signUpDtoArguments1")
    @DisplayName("signup 필드 검증 테스트(존재 유무)")
    public void signUpFieldValidationTest1(String accountId, String pw, String name, String email, int invalidCnt) {
        UserRequestDto.SignUp signUpRequestDto =  UserRequestDto.SignUp.builder()
                .accountId(accountId)
                .pw(pw)
                .name(name)
                .email(email)
                .build();

        Set<ConstraintViolation<UserRequestDto.SignUp>> violations = validator.validate(signUpRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }

    static Stream<Arguments> signUpDtoArguments2() {
        return Stream.of(
                Arguments.arguments("id1sssssssssssssssssssssssssss", "12345", "kim", "kim@naver.com", 1),
                Arguments.arguments("id1sssssssssssssssssssssssssss", "1234525234234232", "kim", "kim@naver.com", 2),
                Arguments.arguments("id1sssssssssssssssssssssssssss", "1234525234234232", "kimkimkimkimkimkimkim", "kim@naver.com", 3),
                Arguments.arguments("id1sssssssssssssssssssssssssss", "1234525234234232", "kimkimkimkimkimkimkim", "kim@naver.comkim@naver.comkim@naver.comkim@naver.comkim@naver.comkim@naver.com", 4)
        );
    }

    @ParameterizedTest
    @MethodSource("signUpDtoArguments2")
    @DisplayName("signup 필드 검증 테스트(길이제한 테스트)")
    public void signUpFieldValidationTest2(String accountId, String pw, String name, String email, int invalidCnt) {
        UserRequestDto.SignUp signUpRequestDto =  UserRequestDto.SignUp.builder()
                .accountId(accountId)
                .pw(pw)
                .name(name)
                .email(email)
                .build();

        Set<ConstraintViolation<UserRequestDto.SignUp>> violations = validator.validate(signUpRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }

    static Stream<Arguments> loginDtoArguments1() {
        return Stream.of(
                Arguments.arguments("id1", "", 1),
                Arguments.arguments("", "12345", 1),
                Arguments.arguments("", "", 2)
        );
    }

    @ParameterizedTest
    @MethodSource("loginDtoArguments1")
    @DisplayName("login 필드 검증 테스트")
    public void loginFieldValidationTest(String accountId, String pw, int invalidCnt) {
        UserRequestDto.Login loginRequestDto =  UserRequestDto.Login.builder()
                .accountId(accountId)
                .pw(pw)
                .build();

        Set<ConstraintViolation<UserRequestDto.Login>> violations = validator.validate(loginRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }
}
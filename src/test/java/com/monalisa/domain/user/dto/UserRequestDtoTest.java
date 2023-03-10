package com.monalisa.domain.user.dto;

import org.assertj.core.api.Assertions;
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

    static Stream<Arguments> arguments1() {
        return Stream.of(
                Arguments.arguments("id1", "12345", "kim", "kim@naver.com", 0),
                Arguments.arguments("", "12345", "", "kim@naver.com", 2),
                Arguments.arguments("id1", "", "kim", "kim@naver.com", 1),
                Arguments.arguments(null, null, null, null, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("arguments1")
    @DisplayName("signup 필드 검증 테스트")
    public void signUpFieldValidationTest(String accountId, String pw, String name, String email, int invalidCnt) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UserRequestDto.SignUp signUpRequestDto =  UserRequestDto.SignUp.builder()
                .accountId(accountId)
                .pw(pw)
                .name(name)
                .email(email)
                .build();

        Set<ConstraintViolation<UserRequestDto.SignUp>> violations = validator.validate(signUpRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }

    static Stream<Arguments> arguments2() {
        return Stream.of(
                Arguments.arguments("id1", "", 1),
                Arguments.arguments("", "12345", 1),
                Arguments.arguments("", "", 2)
        );
    }

    @ParameterizedTest
    @MethodSource("arguments2")
    @DisplayName("login 필드 검증 테스트")
    public void loginFieldValidationTest(String accountId, String pw, int invalidCnt) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UserRequestDto.Login loginRequestDto =  UserRequestDto.Login.builder()
                .accountId(accountId)
                .pw(pw)
                .build();

        Set<ConstraintViolation<UserRequestDto.Login>> violations = validator.validate(loginRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }
}
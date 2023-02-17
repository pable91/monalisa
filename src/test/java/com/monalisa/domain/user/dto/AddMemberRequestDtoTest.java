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

class AddMemberRequestDtoTest {

    static Stream<Arguments> arguments1() {
        return Stream.of(
                Arguments.arguments("name1", 0),
                Arguments.arguments("", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("arguments1")
    @DisplayName("필드 검증 테스트")
    public void fieldValidationTest(String name, int invalidCnt) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name(name)
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }
}
package com.monalisa.domain.book.dto.request;

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

class BookRequestDtoTest {

    static Stream<Arguments> arguments1() {
        return Stream.of(
                Arguments.arguments("name1", "desc1", 10000, "author1", 1L, 0),
                Arguments.arguments("", "desc1", 1000, "author1", 1L, 1),
                Arguments.arguments("", "desc1", 10, "author1", 1L, 2),
                Arguments.arguments("", "", 10, "", 1L, 3),
                Arguments.arguments("", "", 10, "", 0L, 4),
                Arguments.arguments("", "", 100, "author1", 1L, 2)

        );
    }

    @ParameterizedTest
    @MethodSource("arguments1")
    @DisplayName("Add 필드 검증 테스트")
    public void addDtoFieldValidationTest(String name, String desc, Integer cost, String author, Long userId, int invalidCnt) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        BookRequestDto.Add addBookRequestDto = BookRequestDto.Add.builder()
                .name(name)
                .desc(desc)
                .cost(cost)
                .author(author)
                .userId(userId)
                .build();

        Set<ConstraintViolation<BookRequestDto.Add>> violations = validator.validate(addBookRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }

    static Stream<Arguments> arguments2() {
        return Stream.of(
                Arguments.arguments("name1", "desc1", 10000, "author1", 1L, 1L, 0),
                Arguments.arguments("", "desc1", 1000, "author1", 1L, 1L, 1),
                Arguments.arguments("", "desc1", 10, "author1", 1L, 1L, 2),
                Arguments.arguments("", "", 10, "", 1L, 1L, 3),
                Arguments.arguments("", "", 10, "", 0L, 1L, 4),
                Arguments.arguments("", "", 10, "", 0L, 0L, 5),
                Arguments.arguments("", "", 100, "author1", 1L, 1L, 2)

        );
    }

    @ParameterizedTest
    @MethodSource("arguments2")
    @DisplayName("Update 필드 검증 테스트")
    public void updateDtoFieldValidationTest(String name, String desc, Integer cost, String author, Long userId, Long bookId, int invalidCnt) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        BookRequestDto.Update updateBookRequestDto = BookRequestDto.Update.builder()
                .name(name)
                .desc(desc)
                .cost(cost)
                .author(author)
                .userId(userId)
                .bookId(bookId)
                .build();

        Set<ConstraintViolation<BookRequestDto.Update>> violations = validator.validate(updateBookRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }
}
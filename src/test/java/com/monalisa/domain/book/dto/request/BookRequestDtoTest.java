package com.monalisa.domain.book.dto.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

class BookRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void before() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    static Stream<Arguments> addDtoArguments1() {
        return Stream.of(
                Arguments.arguments("name1", "desc1", 10000, "author1", 0),
                Arguments.arguments("", "desc1", 1000, "author1", 1),
                Arguments.arguments("", "desc1", 10, "author1", 2),
                Arguments.arguments("", "", 10, "", 3),
                Arguments.arguments("", "", 100, "author1", 2)

        );
    }

    @ParameterizedTest
    @MethodSource("addDtoArguments1")
    @DisplayName("Add 필드 검증 테스트")
    public void addDtoFieldValidationTest1(String name, String desc, Integer cost, String author, int invalidCnt) {
        BookRequestDto.Add addBookRequestDto = BookRequestDto.Add.builder()
                .name(name)
                .desc(desc)
                .cost(cost)
                .author(author)
                .build();

        Set<ConstraintViolation<BookRequestDto.Add>> violations = validator.validate(addBookRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }

    static Stream<Arguments> addDtoArguments2() {
        return Stream.of(
                Arguments.arguments("name1", "desc1", 10000, "author1", 0),
                Arguments.arguments("name1name1name1name1name1name1name1name1", "desc1", 10000, "author1", 1),
                Arguments.arguments("name1name1name1name1name1name1name1name1", "desc1", 10000, "author1author1author1author1author1author1author1author1", 2)
        );
    }

    @ParameterizedTest
    @MethodSource("addDtoArguments2")
    @DisplayName("Add 필드 검증 테스트(길이제한테스트)")
    public void addDtoFieldValidationTest2(String name, String desc, Integer cost, String author, int invalidCnt) {
        BookRequestDto.Add addBookRequestDto = BookRequestDto.Add.builder()
                .name(name)
                .desc(desc)
                .cost(cost)
                .author(author)
                .build();

        Set<ConstraintViolation<BookRequestDto.Add>> violations = validator.validate(addBookRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }

    static Stream<Arguments> updateDtoArguments1() {
        return Stream.of(
                Arguments.arguments("name1", "desc1", 10000, "author1", 1L, 0),
                Arguments.arguments("", "desc1", 1000, "author1", 1L, 1),
                Arguments.arguments("", "desc1", 10, "author1", 1L, 2),
                Arguments.arguments("", "", 10, "", 1L, 3),
                Arguments.arguments("", "", 10, "", 1L, 3),
                Arguments.arguments("", "", 10, "", 0L, 4),
                Arguments.arguments("", "", 100, "author1", 1L, 2)

        );
    }

    @ParameterizedTest
    @MethodSource("updateDtoArguments1")
    @DisplayName("Update 필드 검증 테스트")
    public void updateDtoFieldValidationTest1(String name, String desc, Integer cost, String author, Long bookId, int invalidCnt) {
        BookRequestDto.Update updateBookRequestDto = BookRequestDto.Update.builder()
                .name(name)
                .desc(desc)
                .cost(cost)
                .author(author)
                .bookId(bookId)
                .build();

        Set<ConstraintViolation<BookRequestDto.Update>> violations = validator.validate(updateBookRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }

    static Stream<Arguments> updateDtoArguments2() {
        return Stream.of(
                Arguments.arguments("name1", "desc1", 10000, "author1", 1L, 0),
                Arguments.arguments("name1name1name1name1name1name1name1name1", "desc1", 10000, "author1", 1L, 1),
                Arguments.arguments("name1name1name1name1name1name1name1name1", "desc1", 10000, "author1author1author1author1author1author1author1author1", 1L, 2)

        );
    }

    @ParameterizedTest
    @MethodSource("updateDtoArguments2")
    @DisplayName("Update 필드 검증 테스트(길이제한테스트)")
    public void updateDtoFieldValidationTest2(String name, String desc, Integer cost, String author, Long bookId, int invalidCnt) {
        BookRequestDto.Update updateBookRequestDto = BookRequestDto.Update.builder()
                .name(name)
                .desc(desc)
                .cost(cost)
                .author(author)
                .bookId(bookId)
                .build();

        Set<ConstraintViolation<BookRequestDto.Update>> violations = validator.validate(updateBookRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }
}
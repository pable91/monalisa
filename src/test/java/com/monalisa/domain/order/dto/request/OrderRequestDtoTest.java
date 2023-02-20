package com.monalisa.domain.order.dto.request;

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


class OrderRequestDtoTest {

    static Stream<Arguments> arguments1() {
        return Stream.of(
                Arguments.arguments(1L, 0L, 1),
                Arguments.arguments(-100L, 2L, 1),
                Arguments.arguments(100L, 100L, 0),
                Arguments.arguments(-100L, -1323L, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("arguments1")
    @DisplayName("OrderRequestDTO Buy 필드 검증 테스트")
    public void orderDTOFieldValidationTest(Long bookId, Long userId, int invalidCnt) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        OrderRequestDto.Buy orderRequestDto  = OrderRequestDto.Buy.builder()
                .bookId(bookId)
                .buyerId(userId)
                .build();


        Set<ConstraintViolation<OrderRequestDto.Buy>> violations = validator.validate(orderRequestDto);
        Assertions.assertThat(violations.size()).isEqualTo(invalidCnt);
    }
}
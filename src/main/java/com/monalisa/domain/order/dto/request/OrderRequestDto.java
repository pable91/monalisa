package com.monalisa.domain.order.dto.request;

import lombok.Getter;

import javax.validation.constraints.Min;

public class OrderRequestDto {

    @Getter
    public static class Buy {

        @Min(1)
        private Long bookId;

        @Min(1)
        private Long userId;
    }
}

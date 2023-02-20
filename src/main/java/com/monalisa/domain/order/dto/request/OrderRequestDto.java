package com.monalisa.domain.order.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;


public class OrderRequestDto {

    @Getter
    @Builder
    public static class Buy {

        @Min(1)
        private Long bookId;

        @Min(1)
        private Long buyerId;
    }
}

package com.monalisa.domain.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.util.List;


public class OrderRequestDto {

    @Getter
    @Builder
    public static class SingleBook {

        @Min(1)
        private Long bookId;

        @Min(1)
        private Long buyerId;
    }

    @Getter
    @ToString
    public static class MultiBook {
        private List<Long> bookIds;
    }
}

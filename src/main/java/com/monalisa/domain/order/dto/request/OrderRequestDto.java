package com.monalisa.domain.order.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
    @Builder
    @ToString
    public static class MultiBook {

        @NotEmpty
        private List<Long> bookIds;
    }
}

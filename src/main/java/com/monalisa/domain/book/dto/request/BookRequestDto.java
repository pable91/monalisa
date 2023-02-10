package com.monalisa.domain.book.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class BookRequestDto {

    @Getter
    @Builder
    @ToString
    public static class Add {

        @NotBlank
        private String name;

        @Lob
        @Length(max = 100, message = "등록된 설명이 없습니다")
        private String desc;

        @Min(500)
        private Integer cost;

        @NotBlank
        private String author;

        @Min(1)
        private Long userId;
    }

    @Getter
    @Builder
    @ToString
    public static class Update{

        @NotBlank
        private String name;

        @Lob
        @Length(max = 100, message = "등록된 설명이 없습니다")
        private String desc;

        @Min(500)
        private Integer cost;

        @NotBlank
        private String author;

        @Min(1)
        private Long userId;

        @Min(1)
        private Long bookId;
    }

}

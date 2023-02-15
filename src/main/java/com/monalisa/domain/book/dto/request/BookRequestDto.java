package com.monalisa.domain.book.dto.request;

import com.monalisa.domain.book.domain.Book;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

        // TODO
        // 나중에 로그인 기능이 있다면 굳이 필요할까?
        @Min(1)
        private Long userId;
    }

    @Getter
    @Builder
    @ToString
    public static class Update {

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

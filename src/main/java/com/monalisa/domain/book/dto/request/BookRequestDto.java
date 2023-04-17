package com.monalisa.domain.book.dto.request;

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
        @Length(max = 30, message = "책 이름의 길이는 30이하 입니다")
        private String name;

        @Lob
        private String desc;

        @Min(500)
        private Integer cost;

        @NotBlank
        @Length(max = 20, message = "저자의 이름의 길이는 20이하입니다")
        private String author;
    }

    @Getter
    @Builder
    @ToString
    public static class Update {

        @NotBlank
        @Length(max = 30, message = "책 이름의 길이는 30이하 입니다")
        private String name;

        @Lob
        private String desc;

        @Min(500)
        private Integer cost;

        @NotBlank
        @Length(max = 20, message = "저자의 이름의 길이는 20이하입니다")
        private String author;

        @Min(1)
        private Long bookId;
    }
}

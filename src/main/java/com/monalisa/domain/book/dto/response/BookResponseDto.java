package com.monalisa.domain.book.dto.response;

import com.monalisa.domain.book.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookResponseDto {
    private String userName;

    private String name;

    private String desc;

    private Integer cost;

    private String author;

    private Boolean isSold;

    private Integer like;

    private BookResponseDto(final Book book) {
        this.userName = book.getUser().getName();
        this.name = book.getName();
        this.desc = book.getDesc();
        this.cost = book.getCost();
        this.author = book.getAuthor();
        this.isSold = book.isSold();
        this.like = book.getLikes();
    }

    public static BookResponseDto of(final Book book) {
        return new BookResponseDto(book);
    }
}

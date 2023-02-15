package com.monalisa.domain.book.dto.response;

import com.monalisa.domain.book.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class BookResponseDto {
    private String name;

    private String desc;

    private Integer cost;

    private String author;

    private Long bookId;

    private BookResponseDto(Book book) {
        this.name = book.getName();
        this.desc = book.getDesc();
        this.cost = book.getCost();
        this.author = book.getAuthor();
        this.bookId = book.getId();
    }

    public static BookResponseDto of(Book book) {
        return new BookResponseDto(book);
    }
}

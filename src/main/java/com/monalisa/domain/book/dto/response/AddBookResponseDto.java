package com.monalisa.domain.book.dto.response;

import com.monalisa.domain.book.domain.Book;
import lombok.Getter;

@Getter
public class AddBookResponseDto {

    private String name;

    public AddBookResponseDto(Book book) {
        this.name = book.getName();
    }
}

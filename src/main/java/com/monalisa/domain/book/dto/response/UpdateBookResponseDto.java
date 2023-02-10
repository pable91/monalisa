package com.monalisa.domain.book.dto.response;

import com.monalisa.domain.book.domain.Book;
import lombok.Getter;

@Getter
public class UpdateBookResponseDto {

    private String name;

    public UpdateBookResponseDto(Book book) {
        this.name = book.getName();
    }
}

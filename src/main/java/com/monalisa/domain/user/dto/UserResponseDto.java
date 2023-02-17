package com.monalisa.domain.user.dto;

import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.user.domain.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserResponseDto {

    private String userName;
    private List<BookResponseDto> bookList;

    public UserResponseDto(User user) {
        this.userName = user.getName();
        bookList = user.getBookList().stream()
                .map(book -> BookResponseDto.of(book))
                .collect(Collectors.toList());
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user);
    }
}

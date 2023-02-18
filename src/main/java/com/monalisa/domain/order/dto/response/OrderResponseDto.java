package com.monalisa.domain.order.dto.response;

import com.monalisa.domain.book.domain.Book;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private String buyerName;

    private String sellerName;

    private String name;

    private Integer cost;

    private OrderResponseDto(Book book) {
        this.sellerName = book.getUser().getName();
        this.name = book.getName();
        this.cost = book.getCost();
    }

    private OrderResponseDto(Book book, String buyerName) {
        this.buyerName = buyerName;
        this.sellerName = book.getUser().getName();
        this.name = book.getName();
        this.cost = book.getCost();
    }

    public static OrderResponseDto of(Book book) {
        return new OrderResponseDto(book);
    }

    public static OrderResponseDto of(Book book, String buyerName) {
        return new OrderResponseDto(book,buyerName);
    }
}

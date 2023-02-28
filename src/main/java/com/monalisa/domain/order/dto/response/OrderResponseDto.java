package com.monalisa.domain.order.dto.response;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.order.domain.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseDto {

    private Integer totalPrice;
    private List<BookResponseDto> bookList;
    private String buyerName;

    @Getter
    public static class Create {
        private String buyerName;

        private String sellerName;

        private String name;

        private Integer cost;

        private Create(Book book) {
            this.sellerName = book.getUser().getName();
            this.name = book.getName();
            this.cost = book.getCost();
        }

        private Create(Book book, String buyerName) {
            this.buyerName = buyerName;
            this.sellerName = book.getUser().getName();
            this.name = book.getName();
            this.cost = book.getCost();
        }

        public static Create of(Book book) {
            return new Create(book);
        }

        public static Create of(Book book, String buyerName) {
            return new Create(book, buyerName);
        }
    }

    @Getter
    public static class Find {
        private Integer totalPrice;
        private LocalDateTime orderDate;
        private List<BookResponseDto> bookList;

        public Find(Order order) {
            this.totalPrice = order.getTotalPrice();
            this.orderDate = order.getCreatedDate();
            bookList = order.getBookList().stream()
                    .map(book -> BookResponseDto.of(book))
                    .collect(Collectors.toList());
        }

        public static Find of(Order order) {
            return new Find(order);
        }
    }
}

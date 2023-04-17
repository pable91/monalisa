package com.monalisa.domain.order.dto.response;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.order.domain.Order;
import com.monalisa.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class OrderResponseDto {

    private Integer totalPrice;
    private List<BookResponseDto> bookList;
    private String buyerName;

    @Getter
    public static class CreateSingle {
        private String buyerName;

        private String sellerName;

        private String buyBookName;

        private Integer cost;

        private CreateSingle(Book book) {
            this.sellerName = book.getUser().getName();
            this.buyBookName = book.getName();
            this.cost = book.getCost();
        }

        private CreateSingle(Book book, String buyerName) {
            this.buyerName = buyerName;
            this.sellerName = book.getUser().getName();
            this.buyBookName = book.getName();
            this.cost = book.getCost();
        }

        public static CreateSingle of(Book book) {
            return new CreateSingle(book);
        }

        public static CreateSingle of(Book book, String buyerName) {
            return new CreateSingle(book, buyerName);
        }
    }

    @Getter
    public static class CreateMulti {
        private String buyerName;

        private int buyBookNum;

        private List<String> buyBookNames;

        private CreateMulti(final List<Book> books, final User buyer) {
            this.buyerName = buyer.getName();
            this.buyBookNum = books.size();
            this.buyBookNames = books.stream()
                    .map(b -> b.getName())
                    .collect(Collectors.toList());
        }

        public static CreateMulti of(final List<Book> books, final User buyer) {
            return new CreateMulti(books, buyer);
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
            this.bookList = order.getBookList().stream()
                    .map(book -> BookResponseDto.of(book))
                    .collect(Collectors.toList());
        }

        public static Find of(Order order) {
            return new Find(order);
        }
    }
}

package com.monalisa.domain.order.domain;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTest {

    private Book newBook;
    private User buyer;

    @BeforeEach()
    public void before() {
        BookRequestDto.Add addBookRequestDto  = BookRequestDto.Add.builder()
                .name("name1")
                .desc("desc1")
                .cost(10000)
                .author("author1")
                .build();

        buyer = User.createTestUser(1L, "kim", "kim@naver.com");

        newBook = Book.registerBook(addBookRequestDto, buyer);
    }

    @Test
    @DisplayName("주문 생성 테스트")
    public void createOrderTest() {
        Order order = Order.createOrderBySingleBook(newBook, buyer);

        Assertions.assertThat(order.getTotalPrice()).isEqualTo(10000);
        Assertions.assertThat(order.getBookList().size()).isEqualTo(1);
        Assertions.assertThat(newBook.isSold()).isEqualTo(true);
        Assertions.assertThat(order.getBuyer().getName()).isEqualTo("kim");
    }
}

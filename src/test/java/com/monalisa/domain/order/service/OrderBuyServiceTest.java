package com.monalisa.domain.order.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.exception.BookAlreadySoldException;
import com.monalisa.domain.book.exception.BuyNotMyBookException;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.service.queryService.BookFindQueryService;
import com.monalisa.domain.order.dto.request.OrderRequestDto;
import com.monalisa.domain.order.dto.response.OrderResponseDto;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.exception.UserNotFoundException;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderBuyServiceTest {

    @InjectMocks
    private OrderBuyService orderBuyService;

    @Mock
    private OrderUpdateQueryService orderUpdateQueryService;

    @Mock
    private UserFindQueryService userFindQueryService;

    @Mock
    private BookFindQueryService bookFindQueryService;

    private User seller;
    private User buyer;
    private Book book;
    private OrderRequestDto.Buy requestDTO;

    @BeforeEach
    public void init() {
        seller = User.createTestUser(1L, "seller");

        buyer = User.createTestUser(2L, "buyer");

        BookRequestDto.Add addBookRequestDto = BookRequestDto.Add.builder()
                .name("book1")
                .desc("desc")
                .cost(1000)
                .author("author")
                .userId(1L)
                .build();

        book = Book.registerBook(addBookRequestDto, seller);

        requestDTO = OrderRequestDto.Buy.builder()
                .buyerId(2L)
                .bookId(1L)
                .build();
    }

    @Test
    @DisplayName("주문 생성 테스트")
    public void createOrderTest() {
        // give
        when(userFindQueryService.findById(any())).thenReturn(buyer);
        when(bookFindQueryService.findById(any())).thenReturn(book);

        // when
        OrderResponseDto responseDto = orderBuyService.createOrder(requestDTO);

        // then
        Assertions.assertThat(responseDto.getBuyerName()).isEqualTo("buyer");
        Assertions.assertThat(responseDto.getSellerName()).isEqualTo("seller");
        Assertions.assertThat(responseDto.getName()).isEqualTo("book1");
        Assertions.assertThat(responseDto.getCost()).isEqualTo(1000);
    }

    @Test
    @DisplayName("buyer가 없을경우 예외를 던진다")
    public void notFoundBuyerExceptionTest() {
        // give
        when(userFindQueryService.findById(any())).thenThrow(UserNotFoundException.class);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, () -> {
             orderBuyService.createOrder(requestDTO);
        });
    }

    @Test
    @DisplayName("사려는 book이 없을경우 예외를 던진다")
    public void notFoundBookExceptionTest() {
        // give
        when(bookFindQueryService.findById(any())).thenThrow(NotFoundBookException.class);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundBookException.class, () -> {
            orderBuyService.createOrder(requestDTO);
        });
    }

    @Test
    @DisplayName("내가 등록한 책을 주문하려고 하면 예외를 던진다")
    public void buyNotMyBookExceptionTest() {
        // give
        when(userFindQueryService.findById(any())).thenReturn(seller);
        when(bookFindQueryService.findById(any())).thenReturn(book);

        OrderRequestDto.Buy dto = OrderRequestDto.Buy.builder()
                .buyerId(1L)
                .bookId(1L)
                .build();

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(BuyNotMyBookException.class, () -> {
            orderBuyService.createOrder(dto);
        });
    }

    @Test
    @DisplayName("주문한 책이 이미 팔린 책이라면 예외를 던진다")
    public void bookAlreadySoldExceptionTest() {
        // give
        when(userFindQueryService.findById(any())).thenReturn(buyer);
        when(bookFindQueryService.findById(any())).thenReturn(book);
        book.setBuyState();

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(BookAlreadySoldException.class, () -> {
            orderBuyService.createOrder(requestDTO);
        });
    }
}
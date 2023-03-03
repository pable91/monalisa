package com.monalisa.domain.order.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.exception.BookAlreadySoldException;
import com.monalisa.domain.book.exception.BuyNotMyBookException;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.service.queryService.BookFindQueryService;
import com.monalisa.domain.order.domain.Order;
import com.monalisa.domain.order.dto.request.OrderRequestDto;
import com.monalisa.domain.order.dto.response.OrderResponseDto;
import com.monalisa.domain.user.domain.User;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

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
    private Book book1;
    private Book book2;
    private Book book3;
    private OrderRequestDto.SingleBook requestDTO;

    @BeforeEach
    public void init() {
        seller = User.createTestUser(1L, "seller", "kim@naver.com");

        buyer = User.createTestUser(2L, "buyer", "kim@naver.com");

        BookRequestDto.Add addBookRequestDto1 = BookRequestDto.Add.builder()
                .name("book1")
                .desc("desc")
                .cost(1000)
                .author("author")
                .userId(1L)
                .build();

        BookRequestDto.Add addBookRequestDto2 = BookRequestDto.Add.builder()
                .name("book2")
                .desc("desc")
                .cost(100000)
                .author("author")
                .userId(1L)
                .build();

        BookRequestDto.Add addBookRequestDto3 = BookRequestDto.Add.builder()
                .name("book3")
                .desc("desc")
                .cost(500)
                .author("author")
                .userId(1L)
                .build();

        book1 = Book.registerBook(addBookRequestDto1, seller);
        book2 = Book.registerBook(addBookRequestDto2, seller);
        book3 = Book.registerBook(addBookRequestDto3, seller);

        requestDTO = OrderRequestDto.SingleBook.builder()
                .bookId(1L)
                .build();

        ////////////////////////

//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(buyer, "", null));
    }

    @Test
    @DisplayName("한권의 책만 주문하는 테스트")
    public void createOrderSingleBookTest() {
        // give
        when(userFindQueryService.findById(any())).thenReturn(buyer);
        when(bookFindQueryService.findById(any())).thenReturn(book1);

        // when
        OrderResponseDto.CreateSingle responseDto = orderBuyService.createOrderBySingleBook(requestDTO, buyer);

        // then
        Assertions.assertThat(responseDto.getBuyerName()).isEqualTo("buyer");
        Assertions.assertThat(responseDto.getSellerName()).isEqualTo("seller");
        Assertions.assertThat(responseDto.getBuyBookName()).isEqualTo("book1");
        Assertions.assertThat(responseDto.getCost()).isEqualTo(1000);
    }

    @Test
    @DisplayName("buyer가 없을경우 예외를 던진다")
    public void notFoundBuyerExceptionTest() {
        // give
        when(userFindQueryService.findById(any())).thenThrow(UserNotFoundException.class);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(UserNotFoundException.class, () -> {
             orderBuyService.createOrderBySingleBook(requestDTO, buyer);
        });
    }

    @Test
    @DisplayName("사려는 book이 없을경우 예외를 던진다")
    public void notFoundBookExceptionTest() {
        // give
        when(bookFindQueryService.findById(any())).thenThrow(NotFoundBookException.class);

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundBookException.class, () -> {
            orderBuyService.createOrderBySingleBook(requestDTO, buyer);
        });
    }

    @Test
    @DisplayName("내가 등록한 책을 주문하려고 하면 예외를 던진다")
    public void buyNotMyBookExceptionTest() {
        // give
        when(userFindQueryService.findById(any())).thenReturn(seller);
        when(bookFindQueryService.findById(any())).thenReturn(book1);

        OrderRequestDto.SingleBook dto = OrderRequestDto.SingleBook.builder()
                .bookId(1L)
                .build();

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(BuyNotMyBookException.class, () -> {
            orderBuyService.createOrderBySingleBook(dto, buyer);
        });
    }

    @Test
    @DisplayName("주문한 책이 이미 팔린 책이라면 예외를 던진다")
    public void bookAlreadySoldExceptionTest() {
        // give
        when(userFindQueryService.findById(any())).thenReturn(buyer);
        when(bookFindQueryService.findById(any())).thenReturn(book1);
        book1.setBuyState();

        // when, then
        org.junit.jupiter.api.Assertions.assertThrows(BookAlreadySoldException.class, () -> {
            orderBuyService.createOrderBySingleBook(requestDTO, buyer);
        });
    }

    @Test
    @DisplayName("여러 책을 주문하는 테스트")
    public void createOrderMultiBookTest() {
        // give
        List<Book> findBooks = List.of(
                book1,
                book2,
                book3
        );

        Order order = Order.createOrderByMultiBook(findBooks, buyer);

        when(bookFindQueryService.findAllByIds(any())).thenReturn(findBooks);
        when(orderUpdateQueryService.save(any())).thenReturn(order);

        OrderRequestDto.MultiBook orderRequest = OrderRequestDto.MultiBook.builder()
                .bookIds(List.of(1L,2L,3L)).build();

        // when
        OrderResponseDto.CreateMulti response = orderBuyService.createOrderByMultiBook(orderRequest, buyer);

        // then
        Assertions.assertThat(response.getBuyBookNum()).isEqualTo(3);
        Assertions.assertThat(response.getBuyBookNames()).isEqualTo(List.of("book1", "book2", "book3"));
    }
}
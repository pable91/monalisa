package com.monalisa.domain.order.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.exception.BookAlreadySoldException;
import com.monalisa.domain.book.exception.BuyNotMyBookException;
import com.monalisa.domain.book.exception.error.BookErrorCode;
import com.monalisa.domain.book.service.queryService.BookFindQueryService;
import com.monalisa.domain.order.domain.Order;
import com.monalisa.domain.order.dto.request.OrderRequestDto;
import com.monalisa.domain.order.dto.response.OrderResponseDto;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderBuyService {

    private final OrderUpdateQueryService orderUpdateQueryService;
    private final UserFindQueryService userFindQueryService;
    private final BookFindQueryService bookFindQueryService;

    public OrderResponseDto createOrder(final OrderRequestDto.Buy requestDto) {
        final User buyer = userFindQueryService.findById(requestDto.getBuyerId());
        final Book targetBook = bookFindQueryService.findById(requestDto.getBookId());

        validate(requestDto, targetBook);

        Order order = Order.createOrder(targetBook);
        orderUpdateQueryService.save(order);

        return OrderResponseDto.of(targetBook, buyer.getName());
    }

    private void validate(OrderRequestDto.Buy requestDto, Book book) {
        if (book.isMine(requestDto.getBuyerId())) {
            throw new BuyNotMyBookException(BookErrorCode.BUY_NOT_MY_BOOK, requestDto.getBookId());
        }

        if(book.isSold()) {
            throw new BookAlreadySoldException(BookErrorCode.BOOK_ALREADY_SOLD, requestDto.getBookId());
        }
    }
}

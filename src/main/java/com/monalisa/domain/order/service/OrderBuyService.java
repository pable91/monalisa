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

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderBuyService {

    private final OrderUpdateQueryService orderUpdateQueryService;
    private final UserFindQueryService userFindQueryService;
    private final BookFindQueryService bookFindQueryService;

    public OrderResponseDto.CreateSingle createOrderBySingleBook(final OrderRequestDto.SingleBook requestDto, final User user) {
        final User buyer = userFindQueryService.findById(user.getId());
        final Book targetBook = bookFindQueryService.findById(requestDto.getBookId());

        validate(buyer, requestDto, targetBook);

        Order order = Order.createOrderBySingleBook(targetBook, buyer);
        orderUpdateQueryService.save(order);

        return OrderResponseDto.CreateSingle.of(targetBook, buyer.getName());
    }

    private void validate(final User buyer, final OrderRequestDto.SingleBook requestDto, final Book book) {
        if (book.isMine(buyer.getId())) {
            throw new BuyNotMyBookException(BookErrorCode.BUY_NOT_MY_BOOK, requestDto.getBookId());
        }

        if(book.isSold()) {
            throw new BookAlreadySoldException(BookErrorCode.BOOK_ALREADY_SOLD, requestDto.getBookId());
        }
    }

    public OrderResponseDto.CreateMulti createOrderByMultiBook(final OrderRequestDto.MultiBook requestDto, final User buyer) {
        final List<Book> bookList = bookFindQueryService.findAllByIds(requestDto.getBookIds());

        // TODO 만약 없는 책을 구입하려 한다면?

        // 판매 책은 제외
        final List<Book> bookListByNotSold = bookList.stream()
                .filter(b -> !b.isSold())
                .collect(Collectors.toUnmodifiableList());

        Order newOrder = Order.createOrderByMultiBook(bookListByNotSold, buyer);

        return OrderResponseDto.CreateMulti.of(orderUpdateQueryService.save(newOrder).getBookList(), buyer);
    }
}

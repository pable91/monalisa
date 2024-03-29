package com.monalisa.domain.order.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.exception.BookAlreadySoldException;
import com.monalisa.domain.book.exception.BuyNotMyBookException;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.exception.error.BookErrorCode;
import com.monalisa.domain.book.service.queryService.BookFindQueryService;
import com.monalisa.domain.order.domain.Order;
import com.monalisa.domain.order.domain.OrderDetail;
import com.monalisa.domain.order.dto.request.OrderRequestDto;
import com.monalisa.domain.order.dto.request.OrderRequestDto.MultiBook;
import com.monalisa.domain.order.dto.response.OrderResponseDto;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import java.util.List;
import java.util.stream.Collectors;
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

    public OrderResponseDto.CreateSingle createOrderBySingleBook(
        final OrderRequestDto.SingleBook requestDto, final User user) {
        User buyer = userFindQueryService.findById(user.getId());
        Book targetBook = bookFindQueryService.findById(requestDto.getBookId());

        validate(buyer, requestDto, targetBook);

        createOrderByMultiBook(buyer, targetBook);

        return OrderResponseDto.CreateSingle.of(targetBook, buyer.getName());
    }

    private void createOrderByMultiBook(User buyer, Book targetBook) {
        Order newOrder = Order.createOrderBySingleBook(targetBook, buyer);
        newOrder.addOrderDetail(OrderDetail.createOrderDetailBySingleBook(newOrder));
        orderUpdateQueryService.save(newOrder);
    }

    private void validate(final User buyer, final OrderRequestDto.SingleBook requestDto,
        final Book book) {
        if (book.isMine(buyer.getId())) {
            throw new BuyNotMyBookException(BookErrorCode.BUY_NOT_MY_BOOK, requestDto.getBookId());
        }

        if (book.isSold()) {
            throw new BookAlreadySoldException(BookErrorCode.BOOK_ALREADY_SOLD,
                requestDto.getBookId());
        }
    }

    public OrderResponseDto.CreateMulti createOrderByMultiBook(
        final OrderRequestDto.MultiBook requestDto, final User buyer) {
        List<Book> bookList = bookFindQueryService.findAllByIds(requestDto.getBookIds());

        validate(requestDto, bookList);

        List<Book> bookListByNotSold = bookList.stream()
            .filter(b -> !b.isSold())
            .toList();

        Order newOrder = createOrderByMultiBook(buyer, bookListByNotSold);

        return OrderResponseDto.CreateMulti.of(orderUpdateQueryService.save(newOrder).getBookList(),
            buyer);
    }

    private static Order createOrderByMultiBook(User buyer, List<Book> bookListByNotSold) {
        Order newOrder = Order.createOrderByMultiBook(bookListByNotSold, buyer);

        List<OrderDetail> detailList = newOrder.getBookList()
            .stream()
            .map(book -> OrderDetail.createOrderDetailBySingleBook(book, newOrder.getBuyer()))
            .collect(Collectors.toList());

        newOrder.setOrderDetailList(detailList);

        return newOrder;
    }

    private void validate(MultiBook requestDto, List<Book> bookList) {
        // 굳이 필요할까?
        if (bookList.size() != requestDto.getBookIds().size()) {
            throw new NotFoundBookException(BookErrorCode.BOOK_NOT_FOUND);
        }
    }
}

package com.monalisa.domain.order.service;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.exception.BookAlreadySoldException;
import com.monalisa.domain.book.exception.NotFoundBookException;
import com.monalisa.domain.book.exception.BuyNotMyBookException;
import com.monalisa.domain.book.exception.error.BookErrorCode;
import com.monalisa.domain.book.repository.BookRepository;
import com.monalisa.domain.order.dto.request.OrderRequestDto;
import com.monalisa.domain.order.dto.response.OrderResponseDto;
import com.monalisa.domain.order.repository.OrderRepository;
import com.monalisa.domain.user.repository.UserRepository;
import com.monalisa.domain.order.domain.Order;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.exception.UserNotFoundException;
import com.monalisa.domain.user.exception.error.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderBuyService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderResponseDto createOrder(final OrderRequestDto.Buy requestDto) {
        Optional<User> user = userRepository.findById(requestDto.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException(requestDto.getUserId(), UserErrorCode.USER_NOT_FOUND);
        }
        final User findUser = user.get();

        Optional<Book> book = bookRepository.findById(requestDto.getBookId());
        if (!book.isPresent()) {
            throw new NotFoundBookException(BookErrorCode.BOOK_NOT_FOUND, requestDto.getBookId());
        }

        final Book findBook = book.get();
        if (findBook.isMine(requestDto.getUserId())) {
            throw new BuyNotMyBookException(BookErrorCode.BUY_NOT_MY_BOOK, requestDto.getBookId());
        }

        if(findBook.isSold()) {
            throw new BookAlreadySoldException(BookErrorCode.BOOK_ALREADY_SOLD, requestDto.getBookId());
        }

        Order order = Order.createOrder(findBook);
        orderRepository.save(order);

        return OrderResponseDto.of(findBook, findUser.getName());
    }
}

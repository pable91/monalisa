package com.monalisa.domain.order.domain;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.user.domain.User;
import com.monalisa.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_total_price")
    private Integer totalPrice;

    @OneToMany(mappedBy = "order")
    private List<Book> bookList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    public Order(final Book book, final User buyer) {
        this.totalPrice = book.getCost();
        this.buyer = buyer;
        bookList.add(book);
        book.setOrder(this);
    }

    public static Order createOrder(final Book book, final User buyer) {
        book.setBuyState();
        return new Order(book, buyer);
    }
}
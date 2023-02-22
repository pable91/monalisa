package com.monalisa.domain.order.domain;

import com.monalisa.domain.book.domain.Book;
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

    private Integer totalPrice;

    @OneToMany(mappedBy = "order")
    private List<Book> bookList = new ArrayList<>();

    public Order(Book book) {
        this.totalPrice = book.getCost();
        bookList.add(book);
        book.setOrder(this);
    }

    public static Order createOrder(Book book) {
        book.setBuyState();
        return new Order(book);
    }
}
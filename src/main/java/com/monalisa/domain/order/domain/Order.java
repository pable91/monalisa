package com.monalisa.domain.order.domain;

import com.monalisa.domain.book.domain.Book;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orderr")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<Book> bookList = new ArrayList<>();

    public Order(Book book) {
        bookList.add(book);
        book.setOrder(this);
    }

    public static Order createOrder(Book book) {
        book.setBuyState();
        return new Order(book);
    }
}
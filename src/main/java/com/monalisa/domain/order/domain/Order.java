package com.monalisa.domain.order.domain;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.user.domain.User;
import com.monalisa.global.domain.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(mappedBy = "order")
    private List<Book> bookList = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @Column(name = "order_total_price")
    private Integer totalPrice;

    private Order(final Book book, final User buyer) {
        this.totalPrice = book.getCost();
        this.buyer = buyer;
        bookList.add(book);
        book.setOrder(this);
    }

    private Order(final List<Book> books, final User buyer) {
        this.totalPrice = books.stream().mapToInt(b -> b.getCost()).sum();
        this.buyer = buyer;
        books.stream().forEach(b -> {
            this.bookList.add(b);
            b.setOrder(this);
        });
    }

    public static Order createOrderBySingleBook(final Book book, final User buyer) {
        book.setBuyState();
        return new Order(book, buyer);
    }

    public static Order createOrderByMultiBook(final List<Book> bookList, final User buyer) {
        bookList.stream().forEach(b -> b.setBuyState());
        return new Order(bookList, buyer);
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetail.add(orderDetail);
        orderDetail.setOrder(this);
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetail = orderDetailList;
        orderDetailList.stream()
            .forEach(orderDetail -> orderDetail.setOrder(this));
    }
}
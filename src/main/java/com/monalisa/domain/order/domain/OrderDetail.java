package com.monalisa.domain.order.domain;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.user.domain.User;
import com.monalisa.global.domain.BaseTimeEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderDetail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String bookName;

    private String buyerName;

    private OrderDetail(String bookName, String buyerName) {
        this.bookName = bookName;
        this.buyerName = buyerName;
    }

    public static OrderDetail createOrderDetailBySingleBook(Order order) {
        return new OrderDetail(order.getBookList().get(0).getName(), order.getBuyer().getName());
    }


    public static OrderDetail createOrderDetailBySingleBook(Book book, User buyer) {
        return new OrderDetail(book.getName(), buyer.getName());
    }

    public void setOrder(Order order) {
        if(this.order == null) {
            this.order = order;
        }
    }
}

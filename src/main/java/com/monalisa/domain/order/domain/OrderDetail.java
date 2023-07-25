package com.monalisa.domain.order.domain;

import com.monalisa.global.domain.BaseTimeEntity;
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

    public OrderDetail(String bookName, String buyerName) {
        this.bookName = bookName;
        this.buyerName = buyerName;
    }

    public static OrderDetail createOrderDetail(Order order) {
        return new OrderDetail(order.getBookList().get(0).getName(), order.getBuyer().getName());
    }

    public void setOrder(Order order) {
        if(this.order == null) {
            this.order = order;
        }
    }
}

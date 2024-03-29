package com.monalisa.domain.book.domain;

import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.order.domain.Order;
import com.monalisa.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "BOOK")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "description", nullable = false)
    private String desc;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "author", nullable = false, length = 20)
    private String author;

    @Column(name = "is_sold")
    private boolean isSold;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "likes")
    private Integer likes;

    private Book(final BookRequestDto.Add addBookRequestDto, final User user) {
        this.name = addBookRequestDto.getName();
        this.desc = addBookRequestDto.getDesc();
        this.author = addBookRequestDto.getAuthor();
        this.cost = addBookRequestDto.getCost();
        this.user = user;
        this.isSold = false;
        this.likes = 0;

        if (user != null) {
            user.getMyRegisterBooks().add(this);
        }
    }

    public static Book registerBook(final BookRequestDto.Add addBookRequestDto, final User user) {
        return new Book(addBookRequestDto, user);
    }

    public boolean isMine(final Long userId) {
        return (user.getId() == userId);
    }

    public void update(final BookRequestDto.Update updateBookRequestDto) {
        this.name = updateBookRequestDto.getName();
        this.desc = updateBookRequestDto.getDesc();
        this.author = updateBookRequestDto.getAuthor();
        this.cost = updateBookRequestDto.getCost();
    }

    public boolean isSold() { return isSold; }

    public void setBuyState() { isSold = true; }

    public void setOrder(Order order) {
        if(this.order == null) {
            this.order = order;
        }
    }

    public void addLike() {
        this.likes += 1;
    }
}

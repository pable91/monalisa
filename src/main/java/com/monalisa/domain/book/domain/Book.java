package com.monalisa.domain.book.domain;

import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.member.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "book")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "desc", nullable = false)
    private String desc;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "author", nullable = false)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    @Column(name = "isSold")
    private boolean isSold;

    private Book(final BookRequestDto.Add addBookRequestDto, final User user) {
        this.name = addBookRequestDto.getName();
        this.desc = addBookRequestDto.getDesc();
        this.author = addBookRequestDto.getAuthor();
        this.cost = addBookRequestDto.getCost();
        this.user = user;
        this.isSold = false;
    }

    public static Book of(final BookRequestDto.Add addBookRequestDto, final User user) {
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
}

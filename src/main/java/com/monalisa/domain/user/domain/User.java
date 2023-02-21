package com.monalisa.domain.user.domain;

import com.monalisa.domain.book.domain.Book;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

    //    @Column(name = "id", nullable = false)
    @Column(name = "account_id")
    private String accountID;

    //    @Column(name = "pw", nullable = false)
    @Column(name = "pw")
    private String pw;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Book> bookList = new ArrayList<>();

    private User(final String name) {
        this.name = name;
    }

    private User(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    private User(final String accountID, final String pw, final String name) {
        this.accountID = accountID;
        this.pw = pw;
        this.name = name;
    }

    public static User of(final String name) {
        return new User(name);
    }

    public static User from(final Long id, final String name) {
        return new User(id, name);
    }

    public static User createUser(String accountId, String pw, String name) {
        return new User(accountId, pw, name);
    }
}

package com.monalisa.domain.user.domain;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.global.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "USER")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private String accountID;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Book> registerBooks = new ArrayList<>();

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

    public static User createTestUser(final Long id, final String name) {
        return new User(id, name);
    }

    public static User createUser(String accountId, String pw, String name) {
        return new User(accountId, pw, name);
    }
}

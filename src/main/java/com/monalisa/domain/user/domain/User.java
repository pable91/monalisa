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

    @Column(name = "account_id", nullable = false, length = 20)
    private String accountID;

    @Column(name = "pw", nullable = false, length = 70)
    private String pw;

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "email", nullable = false, length = 20)
    private String email;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Book> registerBooks = new ArrayList<>();

    private User(final String name) {
        this.name = name;
    }

    private User(final Long id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = Role.NORMAL;
    }

    private User(final String accountID, final String pw, final String name, final String email) {
        this.accountID = accountID;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.role = Role.NORMAL;
    }

    public static User of(final String name) {
        return new User(name);
    }

    public static User createTestUser(final Long id, final String name, final String email) {
        return new User(id, name, email);
    }

    public static User createUser(String accountId, String pw, String name, final String email) {
        return new User(accountId, pw, name, email);
    }
}

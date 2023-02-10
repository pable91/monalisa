package com.monalisa.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "user")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private User(final String name) {
        this.name = name;
    }

    private User(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static User of(final String name) {
        return new User(name);
    }

    public static User from(final Long id, final String name) {
        return new User(id, name);
    }
}

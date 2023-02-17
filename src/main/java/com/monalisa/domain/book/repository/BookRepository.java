package com.monalisa.domain.book.repository;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByNameAndUser(String name, User user);
}

package com.monalisa.domain.book.repository;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByNameAndUser(String name, User user);

    //    Optional<Book> findByNameAndUser(String name, User user);

    @Query(value = "select b from Book b where b.id in :bookIds")
    List<Book> findAllByIds(@Param("bookIds") List<Long> bookIds);

//    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
//    @Query(value = "select b from Book b where b.id = :id")
//    Optional<Book> findByIdWithPessimisticLock(Long id);
//
//    @Lock(value = LockModeType.OPTIMISTIC)
//    @Query(value = "select b from Book b where b.id = :id")
//    Optional<Book> findByIdWithOptimisticLock(Long id);
}

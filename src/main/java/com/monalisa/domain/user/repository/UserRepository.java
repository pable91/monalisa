package com.monalisa.domain.user.repository;

import com.monalisa.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByAccountID(String ID);
    boolean existsByAccountID(String ID);
}

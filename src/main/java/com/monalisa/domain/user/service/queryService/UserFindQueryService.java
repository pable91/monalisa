package com.monalisa.domain.user.service.queryService;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.exception.UserNotFoundException;
import com.monalisa.domain.user.exception.error.UserErrorCode;
import com.monalisa.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFindQueryService {

    private final UserRepository userRepository;

    public User findById(final Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException(userId, UserErrorCode.USER_NOT_FOUND);
        });
    }
}

package com.monalisa.domain.user.service.queryService;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateQueryService {

    private final UserRepository userRepository;

    public User save(final User user) {
        return userRepository.save(user);
    }
}

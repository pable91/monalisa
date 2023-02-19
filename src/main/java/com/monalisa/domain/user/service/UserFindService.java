package com.monalisa.domain.user.service;

import com.monalisa.domain.user.repository.UserRepository;
import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserResponseDto;
import com.monalisa.domain.user.exception.UserNotFoundException;
import com.monalisa.domain.user.exception.error.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserFindService {

    private final UserRepository userRepository;

//    public void signup(final UserRequestDto userRequestDto) {
//        User newUser = User.of(userRequestDto.getName());
//        userRepository.save(newUser);
//    }

    @Transactional(readOnly = true)
    public UserResponseDto profile(final Long userId) {
        final User findUser = userRepository.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException(userId, UserErrorCode.USER_NOT_FOUND);
        });

        return UserResponseDto.of(findUser);
    }
}

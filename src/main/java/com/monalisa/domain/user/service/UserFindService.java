package com.monalisa.domain.user.service;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserResponseDto;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserFindService {

    private final UserFindQueryService userFindQueryService;

//    public void signup(final UserRequestDto userRequestDto) {
//        User newUser = User.of(userRequestDto.getName());
//        userRepository.save(newUser);
//    }

    @Transactional(readOnly = true)
    public UserResponseDto profile(final Long userId) {
        final User findUser = userFindQueryService.findById(userId);

        return UserResponseDto.of(findUser);
    }
}

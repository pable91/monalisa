package com.monalisa.domain.user.service;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.dto.response.UserResponseDto;
import com.monalisa.domain.user.exception.AlreadyExistUserException;
import com.monalisa.domain.user.exception.WrongPasswordException;
import com.monalisa.domain.user.exception.error.UserErrorCode;
import com.monalisa.domain.user.service.queryService.UserFindQueryService;
import com.monalisa.domain.user.service.queryService.UserUpdateQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserFindQueryService userFindQueryService;
    private final UserUpdateQueryService userUpdateQueryService;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDto.Profile profile(final Long userId) {
        final User findUser = userFindQueryService.findById(userId);

        return UserResponseDto.Profile.of(findUser);
    }

    public UserResponseDto.SignUp signup(final UserRequestDto.SignUp signupUserDto) {
        String accountId = signupUserDto.getAccountId();

        if(userFindQueryService.existByAccountId(accountId)) {
            throw new AlreadyExistUserException(UserErrorCode.ALREADY_EXIST_USER, accountId);
        }

        String pw = passwordEncoder.encode(signupUserDto.getPw());
        User user = User.createUser(accountId, pw, signupUserDto.getName(), signupUserDto.getEmail());
        return UserResponseDto.SignUp.of(userUpdateQueryService.save(user));
    }

    @Transactional(readOnly = true)
    public User login(final UserRequestDto.Login loginUserDto) {
        String accountId = loginUserDto.getAccountId();

        User findUser = userFindQueryService.findByAccountID(accountId);

        if(!passwordEncoder.matches(loginUserDto.getPw(), findUser.getPw())) {
            throw new WrongPasswordException(UserErrorCode.WRONG_PASSWORD, loginUserDto.getPw());
        }

        return findUser;
    }
}

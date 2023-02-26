package com.monalisa.domain.user.api;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.dto.response.UserResponseDto;
import com.monalisa.domain.user.service.UserService;
import com.monalisa.global.config.security.jwt.JwtTokenProvider;
import com.monalisa.global.config.security.jwt.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserApi {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto.SignUp> signUp(@RequestBody @Valid final UserRequestDto.SignUp signupUserDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.signup(signupUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto.Login> login(@RequestBody @Valid final UserRequestDto.Login loginUserDto) {
        User user = userService.login(loginUserDto);

        Token token = jwtTokenProvider.createToken(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("ACCESS_TOKEN", token.getAccessToken());
        httpHeaders.add("REFRESH_TOKEN", token.getRefreshToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(UserResponseDto.Login.from(token, user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto.Profile> profile(@PathVariable final Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.profile(userId));
    }
}

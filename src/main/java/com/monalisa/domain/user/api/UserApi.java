package com.monalisa.domain.user.api;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.dto.UserResponseDto;
import com.monalisa.domain.user.service.UserService;
import com.monalisa.global.config.security.jwt.JwtTokenProvider;
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
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid final UserRequestDto.singUp signupUserDto) {

        System.out.println("test");

        User newUser = userService.signup(signupUserDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UserResponseDto(newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid final UserRequestDto.login loginUserDto) {
        User user = userService.login(loginUserDto);
        String token = jwtTokenProvider.createToken(user.getAccountID());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("JWT", token);

        return new ResponseEntity(token , httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity profile(@PathVariable final Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.profile(userId));
    }
}

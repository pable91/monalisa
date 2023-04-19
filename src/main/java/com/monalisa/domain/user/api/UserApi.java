package com.monalisa.domain.user.api;

import com.monalisa.domain.user.domain.User;
import com.monalisa.domain.user.dto.UserRequestDto;
import com.monalisa.domain.user.dto.response.UserResponseDto;
import com.monalisa.domain.user.service.UserService;
import com.monalisa.global.LoginUser;
import com.monalisa.global.config.security.jwt.JwtTokenProvider;
import com.monalisa.global.config.security.jwt.RefreshToken;
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

        String accessToken = jwtTokenProvider.createAccessToken(user);
        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("ACCESS_TOKEN", accessToken);
        httpHeaders.add("REFRESH_TOKEN", refreshToken.getRefreshToken());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(UserResponseDto.Login.from(accessToken, refreshToken.getRefreshToken(), user));
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestHeader("REFRESH_TOKEN") final String refreshToken, @RequestParam("accountId") final String accountId) {
        User user = userService.findByAccountId(accountId);

        String newAccessToken = jwtTokenProvider.recreateAccessToken(refreshToken, user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("ACCESS_TOKEN", newAccessToken);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeaders)
                .body(newAccessToken);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto.Profile> profile(@LoginUser final User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.profile(user));
    }

    @GetMapping("/orderList")
    public ResponseEntity<UserResponseDto.OrderList> findMyOrderList(@LoginUser final User user) {
        Long userId = (user == null) ? null : user.getId();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findOrderList(userId));
    }
}

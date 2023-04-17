package com.monalisa.global.config.security.jwt.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JwtErrorCode {

    INCORRECT("잘못된 token 형식이거나 로그인이 필요합니다."),
    EXPIRED("토큰이 만료되었습니다. refresh token을 보내주세요.");

    private String message;

}

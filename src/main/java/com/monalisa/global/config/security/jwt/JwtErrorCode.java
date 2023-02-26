package com.monalisa.global.config.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JwtErrorCode {

    EXPIRED("토큰이 만료되었습니다. refresh token을 보내주세요.");

    private String message;

}

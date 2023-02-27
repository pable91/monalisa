package com.monalisa.global.config.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {

    private String accessToken;
    private RefreshToken refreshToken;
}

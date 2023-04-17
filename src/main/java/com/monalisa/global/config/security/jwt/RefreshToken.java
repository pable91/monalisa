package com.monalisa.global.config.security.jwt;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 60)
@Getter
public class RefreshToken {

    @Id
    private String refreshToken;
    private String accountId;

    public RefreshToken(final String refreshToken, final String accountId) {
        this.refreshToken = refreshToken;
        this.accountId = accountId;
    }
}

package com.monalisa.domain.book.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockBookRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(final Long key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(String.valueOf(key), "lock", Duration.ofMillis(3_000));
    }

    public void unlock(final Long key) {
        redisTemplate.delete(String.valueOf(key));
    }
}

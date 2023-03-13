package com.monalisa.domain.book.service.facade;

import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.service.BookUpdateService;
import com.monalisa.global.error.errorcode.CommonErrorCode;
import com.monalisa.global.error.exception.TooManyExcessException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Primary
@Component
@RequiredArgsConstructor
public class RedissonLockBookFacadeFacade implements LikeBookFacade {

    private final RedissonClient redissonClient;
    private final BookUpdateService bookUpdateService;

    public BookResponseDto likeBook(final Long bookId) throws InterruptedException{
        RLock lock = redissonClient.getLock(bookId.toString());

        try {
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

            if(!available) {
                throw new TooManyExcessException(CommonErrorCode.TOO_MANY_EXCESS);
            }

            return bookUpdateService.likeBook(bookId);
        } finally {
            lock.unlock();
        }
    }
}

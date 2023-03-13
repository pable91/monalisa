package com.monalisa.domain.book.service.facade;

import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.book.repository.RedisLockBookRepository;
import com.monalisa.domain.book.service.BookUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LettuceLockBookFacadeFacade implements LikeBookFacade {

    private final BookUpdateService bookUpdateService;

    private final RedisLockBookRepository redisLockBookRepository;

    public BookResponseDto likeBook(final Long bookId) throws InterruptedException {
        while(!redisLockBookRepository.lock(bookId)) {
            Thread.sleep(100);
        }

        try {
            return bookUpdateService.likeBook(bookId);
        } finally {
            redisLockBookRepository.unlock(bookId);
        }
    }
}

package com.monalisa.concurrency;

import com.monalisa.domain.book.domain.Book;
import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.book.repository.BookRepository;
import com.monalisa.domain.book.service.facade.RedissonLockBookFacadeFacade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class LikeConcurrencyTest {

    @Autowired
    private RedissonLockBookFacadeFacade redissonLockBookFacade;
//    private LettuceLockBookFacade lettuceLockBookFacade;

    @Autowired
    private BookRepository bookRepository;


    private Book testBook;

    @BeforeEach
    public void before() {

        BookRequestDto.Add addBookRequestDto = BookRequestDto.Add.builder()
                .name("kim")
                .desc("desc")
                .cost(1000)
                .author("author")
                .build();

        Book book = Book.registerBook(addBookRequestDto, null);

        testBook = bookRepository.save(book);
    }

    @Test
    public void likeConcurrencyTest() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(33);
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; ++i) {
            executorService.submit(() -> {
                try {
                    redissonLockBookFacade.likeBook(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        executorService.shutdown();

        Book book = bookRepository.findById(1L).orElseThrow();

        Assertions.assertThat(book.getLikes()).isEqualTo(100);
    }
}

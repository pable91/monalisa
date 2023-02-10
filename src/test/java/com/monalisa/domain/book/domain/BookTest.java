package com.monalisa.domain.book.domain;

import com.monalisa.domain.book.dto.request.BookRequestDto;
import com.monalisa.domain.member.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookTest {

    private Book newBook;

    @BeforeEach()
    public void before() {
        BookRequestDto.Add addBookRequestDto  = BookRequestDto.Add.builder()
                .name("name1")
                .desc("desc1")
                .cost(10000)
                .author("author1")
                .userId(1L)
                .build();

        User user = User.from(1L, "kim");

        newBook = Book.of(addBookRequestDto, user);
    }

    @Test
    @DisplayName("Book 생성 테스트")
    public void createBookTest() {
        Assertions.assertThat(newBook.getName()).isEqualTo("name1");
        Assertions.assertThat(newBook.getDesc()).isEqualTo("desc1");
        Assertions.assertThat(newBook.getCost()).isEqualTo(10000);
        Assertions.assertThat(newBook.getAuthor()).isEqualTo("author1");
        Assertions.assertThat(newBook.getUser().getId()).isEqualTo(1L);
        Assertions.assertThat(newBook.isSold()).isFalse();
    }

    @Test
    @DisplayName("책을 등록한 User와 수정하려는 User가 같은지 확인하는 테스트")
    public void isMineTest() {
        Long TestUserId1 = 1L;
        Long TestUserId2 = 1000L;

        Assertions.assertThat(newBook.isMine(TestUserId1)).isTrue();
        Assertions.assertThat(newBook.isMine(TestUserId2)).isFalse();
    }

    @Test
    @DisplayName("책 Update 테스트")
    public void updateBookTest() {
        BookRequestDto.Update updateBookRequestDto = BookRequestDto.Update.builder()
                .name("name2")
                .desc("desc2")
                .cost(500)
                .author("author2")
                .userId(1L)
                .bookId(1L)
                .build();

        newBook.update(updateBookRequestDto);

        Assertions.assertThat(newBook.getName()).isEqualTo("name2");
        Assertions.assertThat(newBook.getDesc()).isEqualTo("desc2");
        Assertions.assertThat(newBook.getCost()).isEqualTo(500);
        Assertions.assertThat(newBook.getAuthor()).isEqualTo("author2");
    }
}
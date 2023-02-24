package com.monalisa.domain.user.dto.response;

import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.user.domain.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserResponseDto {

    @Getter
    public static class SignUp {
        private String accountId;

        private String name;

        public SignUp(User user) {
            this.accountId = user.getAccountID();
            this.name = user.getName();
        }

        public static SignUp of(final User user) {
            return new SignUp(user);
        }
    }

    @Getter
    public static class Profile {
        private String userName;
        private List<BookResponseDto> registerBookList;

        public Profile(User user) {
            this.userName = user.getName();
            registerBookList = user.getBookList().stream()
                    .map(book -> BookResponseDto.of(book))
                    .collect(Collectors.toList());
        }

        public static Profile of(User user) {
            return new Profile(user);
        }
    }

    @Getter
    public static class Login {
        private String userName;

        private List<BookResponseDto> registerBookList;

        private String token;

        public Login(String token, User user) {
            this.token = token;
            this.userName = user.getName();
            registerBookList = user.getBookList().stream()
                    .map(book -> BookResponseDto.of(book))
                    .collect(Collectors.toList());
        }

        public static Login from(String token, User user) {
            return new Login(token, user);
        }
    }
}

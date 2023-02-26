package com.monalisa.domain.user.dto.response;

import com.monalisa.domain.book.dto.response.BookResponseDto;
import com.monalisa.domain.user.domain.Role;
import com.monalisa.domain.user.domain.User;
import com.monalisa.global.config.security.jwt.Token;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserResponseDto {

    @Getter
    public static class SignUp {
        private String accountId;

        private String name;

        private String email;

        public SignUp(User user) {
            this.accountId = user.getAccountID();
            this.name = user.getName();
            this.email = user.getEmail();
        }

        public static SignUp of(final User user) {
            return new SignUp(user);
        }
    }

    @Getter
    public static class Profile {
        private String userName;
        private String name;
        private String email;
        private Role role;
        private List<BookResponseDto> registerBookList;

        public Profile(User user) {
            this.userName = user.getName();
            this.name = user.getName();
            this.email = user.getEmail();
            this.role = user.getRole();
            registerBookList = user.getRegisterBooks().stream()
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

        private String accessToken;
        private String refreshToken;

        public Login(Token token, User user) {
            this.accessToken = token.getAccessToken();
            this.refreshToken = token.getRefreshToken();
            this.userName = user.getName();
            registerBookList = user.getRegisterBooks().stream()
                    .map(book -> BookResponseDto.of(book))
                    .collect(Collectors.toList());
        }

        public static Login from(Token token, User user) {
            return new Login(token, user);
        }
    }
}

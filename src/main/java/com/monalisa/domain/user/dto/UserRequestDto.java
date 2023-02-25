package com.monalisa.domain.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

    @Getter
    @Builder
    public static class SignUp {

        @NotBlank
        private String accountId;

        @NotBlank
        private String pw;

        @NotBlank
        private String name;

        @NotBlank
        private String email;
    }

    @Getter
    @Builder
    public static class Login {
        @NotBlank
        private String accountId;

        @NotBlank
        private String pw;
    }
}

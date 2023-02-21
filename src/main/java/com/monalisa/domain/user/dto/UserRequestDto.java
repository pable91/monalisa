package com.monalisa.domain.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

    @Getter
    @Builder
    public static class singUp {

        @NotBlank
        private String accountId;

        @NotBlank
        private String pw;

        @NotBlank
        private String name;
    }

    @Getter
    @Builder
    public static class login {
        @NotBlank
        private String accountId;

        @NotBlank
        private String pw;
    }
}

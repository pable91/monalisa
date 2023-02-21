package com.monalisa.domain.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank
    private String name;

    @Getter
    public static class singUp {

        @NotBlank
        private String accountId;

        @NotBlank
        private String pw;

        @NotBlank
        private String name;
    }

    @Getter
    public static class login {
        @NotBlank
        private String accountId;

        @NotBlank
        private String pw;
    }
}

package com.monalisa.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class UserRequestDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class SignUp {

        @NotBlank
        @Size(max = 20, message = "아이디의 길이는 0부터 20자리 까지입니다.")
        private String accountId;

        @NotBlank
        @Size(max = 10, message = "비밀번호의 길이는 0부터 10자리 까지입니다.")
        private String pw;

        @NotBlank
        @Size(max = 10, message = "사용자 이름의 길이는 0부터 10자리 까지입니다.")
        private String name;

        @NotBlank
        @Size(max = 20, message = "이메일의 길이는 0부터 20자리 까지입니다.")
        private String email;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Login {

        @NotBlank
        @Size(max = 20, message = "아이디의 길이는 0부터 20자리 까지입니다.")
        private String accountId;

        @NotBlank
        @Size(max = 10, message = "비밀번호의 길이는 0부터 10자리 까지입니다.")
        private String pw;
    }
}

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
}

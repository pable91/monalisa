package com.monalisa.domain.member.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddMemberRequestDto {

    @NotBlank
    private String name;
}

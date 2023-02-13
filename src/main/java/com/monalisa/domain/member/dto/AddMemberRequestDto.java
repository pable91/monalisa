package com.monalisa.domain.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddMemberRequestDto {

    @NotBlank
    private String name;
}

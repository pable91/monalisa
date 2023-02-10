package com.monalisa.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
@Builder
public class AddMemberRequestDto {

    @NotBlank
    private String name;
}

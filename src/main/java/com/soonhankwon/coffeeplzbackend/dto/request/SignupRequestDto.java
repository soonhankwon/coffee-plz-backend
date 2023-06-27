package com.soonhankwon.coffeeplzbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class SignupRequestDto {

    @NotNull
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;
}

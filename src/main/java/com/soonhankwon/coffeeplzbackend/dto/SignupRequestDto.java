package com.soonhankwon.coffeeplzbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
//@Builder
public class SignupRequestDto {
    private String loginId;
    private String password;
    private String email;
    private Long point;
}

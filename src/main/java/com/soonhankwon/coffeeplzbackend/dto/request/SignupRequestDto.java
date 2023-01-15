package com.soonhankwon.coffeeplzbackend.dto.request;

import lombok.AllArgsConstructor;
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

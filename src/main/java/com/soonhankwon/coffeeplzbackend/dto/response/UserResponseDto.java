package com.soonhankwon.coffeeplzbackend.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserResponseDto {
    private String loginId;
    private String email;
    private Long point;

    public UserResponseDto(String loginId, String email, Long point) {
        this.loginId = loginId;
        this.email = email;
        this.point = point;
    }
}

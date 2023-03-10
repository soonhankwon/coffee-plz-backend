package com.soonhankwon.coffeeplzbackend.dto.response;

import com.soonhankwon.coffeeplzbackend.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserResponseDto {
    private String loginId;
    private String email;
    private Long point;

    public UserResponseDto(User user) {
        this.loginId = user.getLoginId();
        this.email = user.getEmail();
        this.point = user.getPoint();
    }
}

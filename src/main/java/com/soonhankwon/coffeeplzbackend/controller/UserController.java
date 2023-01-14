package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Api(tags = "유저 API")
public class UserController {
    private final UserService userService;

    @GetMapping("/user/login")
    public GlobalResponseDto login() {
        return new GlobalResponseDto("200","Success");
    }

    @GetMapping("/user/signup")
    public GlobalResponseDto signupUser() {
        return new GlobalResponseDto("200","Success");
    }

    @PostMapping("user/signup")
    public GlobalResponseDto signupUser(SignupRequestDto signupRequestDto) {
        userService.signupUser(signupRequestDto);
        {
            return new GlobalResponseDto("200","Success");
        }
    }
}

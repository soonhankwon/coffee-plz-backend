package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Api(tags = "유저 API")
public class UserController {
    private final UserService userService;
    @PostMapping("/user/signup")
    public ResponseEntity<GlobalResponseDto> signupUser(SignupRequestDto signupRequestDto) {
        return ResponseEntity.status(200).body(userService.signupUser(signupRequestDto));
    }
    @GetMapping("/user/login")
    public ResponseEntity<GlobalResponseDto> login() {
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.findUser(id));
    }
}

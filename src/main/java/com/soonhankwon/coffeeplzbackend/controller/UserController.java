package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "유저 API")
public class UserController {
    private final UserService userService;
    @PostMapping("/user/signup")
    @Operation(summary = "회원 가입")
    public ResponseEntity<GlobalResponseDto> signupUser(SignupRequestDto signupRequestDto) {
        return ResponseEntity.status(200).body(userService.signupUser(signupRequestDto));
    }
    @GetMapping("/user/login")
    @Operation(summary = "로그인")
    public ResponseEntity<GlobalResponseDto> login() {
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "단일 유저 검색")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.findUser(id));
    }

    @GetMapping("/user")
    @Operation(summary = "전체 유저 검색")
    public ResponseEntity<List<UserResponseDto>>findAllUsers() {
        return ResponseEntity.status(200).body(userService.findAllUsers());
    }
}

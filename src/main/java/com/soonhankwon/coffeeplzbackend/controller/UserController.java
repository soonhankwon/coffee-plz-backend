package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "유저 API")
public class UserController {
    private final UserService userService;
    @PostMapping("/users/signup")
    @Operation(summary = "회원 가입")
    public ResponseEntity<GlobalResponseDto> signupUser(SignupRequestDto signupRequestDto) {
        return ResponseEntity.status(200).body(userService.signupUser(signupRequestDto));
    }
    @GetMapping("/users/login")
    @Operation(summary = "로그인 화면")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @CrossOrigin("*")
    @GetMapping("/users/{id}")
    @Operation(summary = "단일 유저 검색")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.findUser(id));
    }

    @GetMapping("/users")
    @Operation(summary = "전체 유저 검색")
    public ResponseEntity<List<UserResponseDto>>findAllUsers() {
        return ResponseEntity.status(200).body(userService.findAllUsers());
    }
}

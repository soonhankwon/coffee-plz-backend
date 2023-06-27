package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "유저 API")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/users/signup")
    @Operation(summary = "회원 가입")
    public GlobalResDto signupUser(@RequestBody @Validated SignupRequestDto signupRequestDto) {
        return userService.signupUser(signupRequestDto);
    }

    @GetMapping("/users/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그인 화면")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @CrossOrigin("*")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    @Operation(summary = "단일 유저 검색")
    public UserResponseDto findUser(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    @Operation(summary = "전체 유저 검색")
    public List<UserResponseDto>findAllUsers() {
        return userService.findAllUsers();
    }
}

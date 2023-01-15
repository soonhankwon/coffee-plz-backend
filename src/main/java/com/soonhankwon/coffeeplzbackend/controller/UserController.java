package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Api(tags = "유저 API")
public class UserController {
    private final UserService userService;

    @GetMapping("/user/login")
    public ResponseEntity<GlobalResponseDto> login() {
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/user/find")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.findUser(id));
    }

    @PostMapping("/user/signup")
    public ResponseEntity<GlobalResponseDto> signupUser(SignupRequestDto signupRequestDto) {
        userService.signupUser(signupRequestDto);
        return ResponseEntity.status(200).body(userService.signupUser(signupRequestDto));
    }
}

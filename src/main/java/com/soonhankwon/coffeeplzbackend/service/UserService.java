package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public GlobalResponseDto signupUser(SignupRequestDto signupRequestDto) {
        boolean duplicateCheck = userRepository.existsByLoginId(signupRequestDto.getLoginId());
        if (duplicateCheck) throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        else
            userRepository.save(new User(signupRequestDto.getLoginId(), signupRequestDto.getEmail(), signupRequestDto.getPassword(), signupRequestDto.getPoint()));
        return new GlobalResponseDto("Success");
    }

    public UserResponseDto findUser(String loginId) {
        User user = userRepository.findByLoginId(loginId);
        return new UserResponseDto(user);
    }

    public List<UserResponseDto> findAllUsers() {
        List<User> list = userRepository.findAll();
        return list.stream().map(UserResponseDto::new).collect(Collectors.toList());
    }
}

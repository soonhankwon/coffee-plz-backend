package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public GlobalResponseDto signupUser(SignupRequestDto signupRequestDto) {
        boolean duplicateCheck = userRepository.existsByLoginId(signupRequestDto.getLoginId());
        if (duplicateCheck) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        else {
            User user = new User(signupRequestDto.getLoginId(), signupRequestDto.getPassword(), signupRequestDto.getEmail(), 0L);
            userRepository.save(user);
        }
        return new GlobalResponseDto("Success");
    }

    public UserResponseDto findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        return new UserResponseDto(user);
    }

    public List<UserResponseDto> findAllUsers() {
        List<User> list = userRepository.findAll();
        return list.stream().map(UserResponseDto::new).collect(Collectors.toList());
    }
}

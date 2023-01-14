package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signupUser(SignupRequestDto signupRequestDto) {
        boolean duplicateCheck = userRepository.existsByLoginId(signupRequestDto.getLoginId());
        if (duplicateCheck) throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        else
            userRepository.save(new User(signupRequestDto.getLoginId(), signupRequestDto.getEmail(), signupRequestDto.getPassword(), signupRequestDto.getPoint()));
        return "Success";
    }

    public User findUser(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}

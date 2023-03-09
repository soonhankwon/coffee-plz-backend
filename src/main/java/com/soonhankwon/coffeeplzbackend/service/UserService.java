package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public GlobalResponseDto signupUser(SignupRequestDto signupRequestDto) {
        if (isLoginIdDuplicate(signupRequestDto.getLoginId()))
            throw new RequestException(ErrorCode.DUPLICATE_LOGIN_ID);
        userRepository.save(new User(signupRequestDto));
        return new GlobalResponseDto("Success");
    }

    private boolean isLoginIdDuplicate(String userId){
        return userRepository.existsByLoginId(userId);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUsers() {
        List<User> list = userRepository.findAll();
        return list.stream().map(UserResponseDto::new).collect(Collectors.toList());
    }
}

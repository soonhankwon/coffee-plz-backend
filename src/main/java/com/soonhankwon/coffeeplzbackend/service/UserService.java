package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.domain.User;
import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.soonhankwon.coffeeplzbackend.domain.User.createUserResDto;

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
        return createUserResDto(userRepository.findById(id)
                .orElseThrow(NullPointerException::new));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUsers() {
        List<User> list = userRepository.findAll();
        return list.stream()
                .map(User::createUserResDto)
                .collect(Collectors.toList());
    }
}

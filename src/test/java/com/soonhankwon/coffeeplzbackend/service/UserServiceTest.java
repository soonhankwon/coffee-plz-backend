package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.domain.User;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("회원 가입 테스트")
    public void signUpUser() {
        //given
        SignupRequestDto dto = new SignupRequestDto("test", "1234", "test@gmail.com");

        User user = userRepository.findByLoginId(dto.getLoginId());
        when(userRepository.save(any())).thenReturn(user);

        //when
        GlobalResponseDto result = userService.signupUser(dto);
        //then
        verify(userRepository, times(1)).save(any());
        assertThat(result.getMessage(), equalTo("Success"));
    }

    @Test
    @DisplayName("중복된 아이디로 가입시 예외처리 테스트")
    public void signUpUserLoginIdDuplicate() {
        //given
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        User existUser = User.builder()
                .loginId(loginId)
                .build();
        SignupRequestDto dto = new SignupRequestDto(loginId, password, email);
        User user = User.builder()
                .loginId(dto.getLoginId())
                .build();
        //then
        assertThatThrownBy(() -> isExistUser(existUser, user))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 사용자가 존재합니다.");
    }

    private static void isExistUser(User existUser, User user) {
        if(existUser.getLoginId().equals(user.getLoginId()))
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
    }

    @Test
    void findUser() {
        //given
        User user = User.builder().id(1L)
                .loginId("soonhan")
                .email("soonable@gmail.com")
                .password("1234")
                .point(0L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        //when
        UserResponseDto result = userService.findUser(1L);

        //then
        verify(userRepository, times(1)).findById(any());
        assertThat(result.getLoginId(), equalTo(user.getLoginId()));
    }

    @Test
    public void findAllUsers() {
        //given
        List<User> users =
                Arrays.asList(User.builder().id(1L).loginId("soonhan").build(),
                        User.builder().id(2L).loginId("kyuri").build());

        when(userRepository.findAll()).thenReturn(users);

        //when
        List<UserResponseDto> result = userService.findAllUsers();

        //then
        verify(userRepository, times(1)).findAll();
        assertThat(result.get(0).getLoginId(), equalTo(users.get(0).getLoginId()));
    }
}

package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    public void saveNewUser() {
        //given
        SignupRequestDto signupRequestDto = new SignupRequestDto("soonhan", "soonable@gmail.com", "1234");

        User user = userRepository.findByLoginId(signupRequestDto.getLoginId());
        when(userRepository.save(any())).thenReturn(user);

        //when
        GlobalResponseDto result = userService.signupUser(new SignupRequestDto
                ("soonhan", "soonable@gmail.com", "1234"));
        //then
        verify(userRepository, times(1)).save(any());
        assertThat(result.getMessage(), equalTo("Success"));
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

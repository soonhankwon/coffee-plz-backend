package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import com.soonhankwon.coffeeplzbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

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
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .loginId("soonhan")
                .email("soonable@gmail.com")
                .password("1234")
                .point(0L).build();

        User user = userRepository.findByLoginId(signupRequestDto.getLoginId());
        when(userRepository.save(any())).thenReturn(user);

        //when
        String result = userService.signupUser(SignupRequestDto.builder().loginId("soonhan").build());
        //then
        verify(userRepository, times(1)).save(any());
        assertThat(result, equalTo("success"));
    }

    @Test void findUser() {
        //given
        User user = User.builder().id(1L)
                .loginId("soonhan")
                .email("soonable@gmail.com")
                .password("1234")
                .point(0L).build();
        when(userRepository.findByLoginId("soonhan")).thenReturn(user);

        //when
        User result = userService.findUser("soonhan");

        //then
        verify(userRepository, times(1)).findByLoginId(any());
        assertThat(result, equalTo(user));
    }

    @Test
    public void findAllUsers() {
        //given
        List<User> users =
                Arrays.asList(User.builder().id(1L).loginId("soonhan").build(),
                        User.builder().id(2L).loginId("kyuri").build());

        when(userRepository.findAll()).thenReturn(users);

        //when
        List<User> result = userService.findAllUsers();

        //then
        verify(userRepository, times(1)).findAll();
        assertThat(result,equalTo(users));
    }
}

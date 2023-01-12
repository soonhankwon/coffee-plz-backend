package com.soonhankwon.coffeeplzbackend.repository;

import com.soonhankwon.coffeeplzbackend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @Mock
    UserRepository userRepository;

    @Test
    void saveTest() {
        //given
        User user = User.builder().id(1L)
                .loginId("soonhan")
                .email("soonable@gmail.com")
                .password("1234")
                .point(0L).build();

        when(userRepository.save(any())).thenReturn(user);
        //when
        User result = userRepository.save(User.builder().loginId("soonhan").build());
        //then
        verify(userRepository, times(1)).save(any());
        assertThat(result, equalTo(user));
    }
    @Test
    void findByLoginId() {
        //given
        User user = User.builder().id(1L)
                .loginId("soonhan")
                .email("soonable@gmail.com")
                .password("1234")
                .point(0L).build();

        when(userRepository.findByLoginId(any())).thenReturn(user);
        //when
        User result = userRepository.findByLoginId("soonhan");
        //then
        verify(userRepository, times(1)).findByLoginId(any());
        assertThat(result.getLoginId(), equalTo(user.getLoginId()));
    }

    @Test
    void findAll() {
        //given
        List<User> users =
                Arrays.asList(User.builder().id(1L).loginId("soonhan").build(),
                        User.builder().id(2L).loginId("kyuri").build());

        when(userRepository.findAll()).thenReturn(users);
        //when
        List<User> result = userRepository.findAll();
        //then
        verify(userRepository, times(1)).findAll();
        assertThat(result.get(0).getLoginId(), equalTo(users.get(0).getLoginId()));
        assertThat(result.get(1).getLoginId(), equalTo(users.get(1).getLoginId()));
    }
}
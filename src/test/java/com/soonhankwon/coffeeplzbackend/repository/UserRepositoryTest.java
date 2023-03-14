package com.soonhankwon.coffeeplzbackend.repository;

import com.soonhankwon.coffeeplzbackend.domain.User;
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
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        User user = new User(1L, loginId, password, email);

        when(userRepository.save(any())).thenReturn(user);
        //when
        User result = userRepository.save(user);
        //then
        verify(userRepository, times(1)).save(any());
        assertThat(result, equalTo(user));
    }

    @Test
    void findByLoginId() {
        //given
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        Long point = 15000L;
        User user = new User(1L, loginId, password, email, point);

        when(userRepository.findByLoginId(any())).thenReturn(user);
        //when
        User result = userRepository.findByLoginId("soonhan");
        //then
        verify(userRepository, times(1)).findByLoginId(any());
    }

    @Test
    void findAll() {
        //given
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        Long point = 15000L;
        User user = new User(1L, loginId, password, email, point);

        String loginId2 = "test2";
        String password2 = "1234";
        String email2 = "test2@gmail.com";
        Long point2 = 15000L;
        User user2 = new User(2L, loginId2, password2, email2, point2);

        List<User> users =
                Arrays.asList(user, user2);

        when(userRepository.findAll()).thenReturn(users);
        //when
        List<User> result = userRepository.findAll();
        //then
        verify(userRepository, times(1)).findAll();
    }
}
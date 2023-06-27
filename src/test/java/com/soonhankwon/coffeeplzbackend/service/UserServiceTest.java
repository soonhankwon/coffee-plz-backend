package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.domain.User;
import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResDto;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
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
        GlobalResDto result = userService.signupUser(dto);
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
        SignupRequestDto dto = new SignupRequestDto(loginId, password, email);
        User user = new User(dto);
        User user2 = new User(dto);
        //then
        assertThatThrownBy(() -> isExistUser(user, user2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 사용자가 존재합니다.");
    }

    private static void isExistUser(User user1, User user2) {
        throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
    }

    @Test
    void findUser() {
        //given
        Long id = 1L;
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        User user = new User(id, loginId, password, email);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        UserResponseDto result = userService.findUser(1L);

        //then
        verify(userRepository, times(1)).findById(any());
        assertThat(result.getLoginId(), equalTo("test"));
    }

    @Test
    public void findAllUsers() {
        //given
        Long id = 1L;
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        Long id2 = 2L;
        String loginId2 = "test2";
        String password2 = "1234";
        String email2 = "test2@gmail.com";
        List<User> users =
                Arrays.asList(new User(id, loginId, password, email),
                        new User(id2, loginId2, password2, email2));

        when(userRepository.findAll()).thenReturn(users);

        //when
        List<UserResponseDto> result = userService.findAllUsers();

        //then
        verify(userRepository, times(1)).findAll();
        assertThat(result.get(0).getLoginId(), equalTo("test"));
    }
}

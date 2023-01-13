package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.PointRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    PointService pointService;

    @Test
    void chargePoint() {
        //given
        User user = User.builder().id(1L)
                .loginId("soonhan")
                .email("soonable@gmail.com")
                .password("1234")
                .point(0L).build();

        userRepository.save(user);
        when(userRepository.findByLoginId(any())).thenReturn(user);

        //when
        String result = pointService.chargePoint("soonhan",10000L);

        //then
        assertThat(user.getPoint(), equalTo(10000L));
        assertThat(result, equalTo("Success"));
    }
}
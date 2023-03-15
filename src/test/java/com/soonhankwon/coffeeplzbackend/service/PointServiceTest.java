package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.PointResponseDto;
import com.soonhankwon.coffeeplzbackend.domain.PointHistory;
import com.soonhankwon.coffeeplzbackend.domain.User;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        Long point = 15000L;
        User user = new User(1L, loginId, password, email, point);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        PointHistory pointHistory = PointHistory.createPointHistory(user, PointHistory.PointType.CHARGE, 10000L);
        //when
        PointResponseDto result = pointService.chargePoint(1L,10000L);

        //then
        assertThat(pointHistory.getPoint(), equalTo(10000L));
        assertThat(result.getMessage(), equalTo("포인트 충전 완료"));
    }
}
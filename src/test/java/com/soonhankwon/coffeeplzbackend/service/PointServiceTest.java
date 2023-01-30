package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.PointResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.PointHistory;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.PointHistoryRepository;
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
    @Mock
    PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    PointService pointService;

    @Test
    void chargePoint() {
        //given
        User user = User.builder().id(1L)
                .loginId("soonhan")
                .email("soonable@gmail.com")
                .password("1234")
                .point(10000L).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        PointHistory pointHistory = new PointHistory(user, PointHistory.PointType.CHARGE, 10000L);
        //when
        PointResponseDto result = pointService.chargePoint(1L,10000L);

        //then
        assertThat(user.getPoint(), equalTo(20000L));
        assertThat(pointHistory.getPoint(), equalTo(10000L));
        assertThat(result.getMessage(), equalTo("포인트 충전 완료"));
    }
}
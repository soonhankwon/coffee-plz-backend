package com.soonhankwon.coffeeplzbackend;

import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.PointHistoryRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import com.soonhankwon.coffeeplzbackend.service.PointService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointChargeOptimisticLockTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    private PointService pointService;


    @Test
    @DisplayName("낙관적 락 포인트 충전 선점 테스트")
    void pointChargeOptimisticLock() throws InterruptedException {
        User user = User.builder().id(1L)
                .loginId("tester")
                .email("tester@gmail.com")
                .password("1234")
                .point(0L).build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        AtomicInteger failedCounter = new AtomicInteger(0);
        IntStream.range(0, 3).forEach(i -> executorService.submit(() -> {
                    try {
                        pointService.chargePoint(1L, 10000L);
                    } catch (OptimisticLockingFailureException e) {
                        failedCounter.incrementAndGet();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
        ));
        countDownLatch.await();
        assert user != null;
        assertEquals(10000L, user.getPoint());
    }
}

package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedissonLockGenerator {
    private final OrderServiceImpl orderService;
    private final RedissonClient redissonClient;

    protected OrderResponseDto executeOrderWithLock(OrderDto orderDto) {
        RLock lock = redissonClient.getLock(String.valueOf(orderDto.getUserId()));
        String worker = Thread.currentThread().getName();
        OrderResponseDto orderResponseDto;
        try {
            boolean available = lock.tryLock(0, 2, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("Lock 을 획득하지 못했습니다.");
            }
            log.info("현재 {}서버에서 작업중입니다.", worker);
            orderResponseDto = orderService.orderProcessing(orderDto);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return orderResponseDto;
    }
}

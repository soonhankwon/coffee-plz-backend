package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public String paymentProcessing(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(NullPointerException::new);
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        long userPoint;
        if (user.getPoint() >= order.getTotalPrice())
            userPoint = user.getPoint() - order.getTotalPrice();
        else
            throw new RuntimeException("포인트가 부족합니다.");
        user.setUserPoint(userPoint);
        return "Success";
    }
}

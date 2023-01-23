package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.PaymentResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.entity.PointHistory;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.PointHistoryRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public PaymentResponseDto paymentProcessing(Long userId) {
        Order order = orderRepository.findByUserIdAndStatus(userId,Order.OrderStatus.ORDERED).orElseThrow(NullPointerException::new);
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        long userPoint;
        if (user.getPoint() >= order.getTotalPrice())
            userPoint = user.getPoint() - order.getTotalPrice();
        else
            throw new RuntimeException("포인트가 부족합니다.");

        user.setUserPoint(userPoint);
        PointHistory pointHistory = new PointHistory(user, PointHistory.PointType.USAGE,order.getTotalPrice());
        pointHistoryRepository.save(pointHistory);

        order.setOrderStatus(Order.OrderStatus.PAID);
        return new PaymentResponseDto("결제완료");
    }
}

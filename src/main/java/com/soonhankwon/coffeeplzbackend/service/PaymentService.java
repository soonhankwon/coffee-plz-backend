package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.domain.*;
import com.soonhankwon.coffeeplzbackend.dto.response.PaymentResponseDto;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.PointHistoryRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public PaymentResponseDto paymentProcessing(Long userId) {
        Order order = orderRepository.findByUserIdAndStatus(userId, OrderStatus.ORDERED).orElseThrow(
                () -> new RequestException(ErrorCode.ORDER_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RequestException(ErrorCode.USER_NOT_FOUND));
        if (user.isUserHasEnoughPoint(order)) {
            user.paid(order);
            order.setStatusPaid();
            pointHistoryRepository.save(new PointHistory(user, PointType.USAGE, order.getTotalPrice()));
        }
        return new PaymentResponseDto("결제완료");
    }
}

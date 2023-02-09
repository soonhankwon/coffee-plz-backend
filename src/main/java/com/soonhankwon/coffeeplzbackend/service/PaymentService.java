package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.PaymentResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.entity.PointHistory;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.PointHistoryRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.soonhankwon.coffeeplzbackend.entity.PointHistory.createPointHistory;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public PaymentResponseDto paymentProcessing(Long userId) {
        Order order = orderRepository.findByUserIdAndStatus(userId, Order.OrderStatus.ORDERED).orElseThrow(
                () -> new RequestException(ErrorCode.ORDER_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RequestException(ErrorCode.USER_NOT_FOUND));
        user.setUserPointWithSufficientPoint(user.getPoint(), order.getTotalPrice());
        pointHistoryRepository.save(createPointHistory(user, PointHistory.PointType.USAGE, order.getTotalPrice()));
        order.setOrderStatus(Order.OrderStatus.PAID);
        return new PaymentResponseDto("결제완료");
    }
}

package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.domain.Order;
import com.soonhankwon.coffeeplzbackend.domain.OrderItem;
import com.soonhankwon.coffeeplzbackend.domain.OrderStatus;
import com.soonhankwon.coffeeplzbackend.domain.User;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderSheetResDto;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderSheetService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public OrderSheetResDto findOrderSheet(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RequestException(ErrorCode.USER_NOT_FOUND));;
        Order order = orderRepository.findOrderByUserAndStatus(user, OrderStatus.ORDERED).orElseThrow(
                () -> new RequestException(ErrorCode.ORDER_NOT_FOUND));
        List<OrderItem> orderItems = order.getOrderItems();
        return OrderItem.createOrderSheet(orderItems);
    }
}

package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<OrderResponseDto> findAllOrder() {
        List<Order> list = orderRepository.findAll();
        return list.stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

    public String orderProcessing(String loginId, OrderRequestDto orderRequestDto) {
        User user = userRepository.findByLoginId(loginId);

        return "주문완료";
    }
}

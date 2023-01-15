package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public List<OrderResponseDto> findAllOrder() {
        List<Order> list = orderRepository.findAll();
        return list.stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

    public OrderResponseDto orderProcessing(OrderRequestDto orderRequestDto) {
        Order order = new Order(orderRequestDto);
        orderRepository.save(order);
        return new OrderResponseDto(order);
    }
}

package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> findAllOrders();
    OrderResponseDto placeOrder(OrderDto orderDto);
    OrderResponseDto orderProcessing(OrderDto orderDto);
}

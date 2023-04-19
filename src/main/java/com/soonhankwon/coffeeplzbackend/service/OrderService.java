package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> findAllOrders();
    OrderResponseDto placeOrder(Long userId, List<OrderRequestDto> orderRequestDto);
    OrderResponseDto orderProcessing(Long userId, List<OrderRequestDto> orderRequestDto);
}

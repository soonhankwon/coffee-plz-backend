package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderSheetResDto;

import java.util.List;

public interface OrderServiceSystem {
    List<OrderResponseDto> findAllOrders();
    OrderResponseDto placeOrder(OrderDto orderDto);
    OrderSheetResDto findOrderSheet(Long userId);
}

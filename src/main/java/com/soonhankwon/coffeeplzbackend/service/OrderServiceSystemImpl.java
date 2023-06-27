package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderSheetResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceSystemImpl implements OrderServiceSystem {

    private final OrderService orderServiceImpl;
    private final OrderSheetService orderSheetService;

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAllOrders() {
        return orderServiceImpl.findAllOrders();
    }

    @Override
    public OrderResponseDto placeOrder(OrderDto orderDto) {
        return orderServiceImpl.placeOrder(orderDto);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderSheetResDto findOrderSheet(Long userId) {
        return orderSheetService.findOrderSheet(userId);
    }
}
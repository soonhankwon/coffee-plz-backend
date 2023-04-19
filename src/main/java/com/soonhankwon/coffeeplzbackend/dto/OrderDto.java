package com.soonhankwon.coffeeplzbackend.dto;

import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderDto {
    private final Long userId;
    private final List<OrderRequestDto> orderRequestDto;

    public OrderDto(Long userId, List<OrderRequestDto> orderRequestDto) {
        this.userId = userId;
        this.orderRequestDto = orderRequestDto;
    }
}

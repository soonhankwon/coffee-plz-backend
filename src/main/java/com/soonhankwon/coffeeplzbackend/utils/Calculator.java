package com.soonhankwon.coffeeplzbackend.utils;

import com.soonhankwon.coffeeplzbackend.domain.ItemSize;
import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Calculator {
    public long calculatePriceSizeAdditionalFee(OrderRequestDto orderRequestDto) {
        if (orderRequestDto.getItemSize().equals(ItemSize.M)) {
            return orderRequestDto.getOrderItemPrice() + ItemSize.M.additionalFee;
        }
        if (orderRequestDto.getItemSize().equals(ItemSize.L)) {
            return orderRequestDto.getOrderItemPrice() + ItemSize.L.additionalFee;
        }
        return orderRequestDto.getOrderItemPrice();
    }

    public long calculateTotalPrice(List<OrderItemDto> orderItemDtoList) {
        return orderItemDtoList.stream()
                .mapToLong(dto -> dto.getOrderItemPrice() * dto.getQuantity())
                .sum();
    }
}

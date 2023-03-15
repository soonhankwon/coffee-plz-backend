package com.soonhankwon.coffeeplzbackend.utils;

import com.soonhankwon.coffeeplzbackend.domain.ItemSize;
import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;

import java.util.List;

public class Calculator {
    public static Long calculatePriceSizeAdditionalFee(OrderRequestDto orderRequestDto) {
        if (orderRequestDto.getItemSize().equals(ItemSize.M)) {
            return orderRequestDto.getOrderItemPrice() + ItemSize.M.additionalFee;
        }
        if (orderRequestDto.getItemSize().equals(ItemSize.L)) {
            return orderRequestDto.getOrderItemPrice() + ItemSize.L.additionalFee;
        }
        return orderRequestDto.getOrderItemPrice();
    }

    public static long calculateTotalPrice(List<OrderItemDto> orderItemDtoList) {
        long totalPrice = 0;
        for (OrderItemDto dto : orderItemDtoList) {
            totalPrice += dto.getOrderItemPrice() * dto.getQuantity();
        }
        return totalPrice;
    }
}

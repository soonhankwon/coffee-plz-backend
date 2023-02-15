package com.soonhankwon.coffeeplzbackend.factory;

import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;

import java.util.List;

public abstract class DataCollectionDtoFactory {
     public static OrderDataCollectionDto createOrderDataCollectionDto(Long userId, List<Long> itemIds, Long totalPrice) {
        return OrderDataCollectionDto.createOrderDataCollectionDto(userId, itemIds, totalPrice);
    }
}

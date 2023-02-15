package com.soonhankwon.coffeeplzbackend.factory;

import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;

import java.util.List;

public abstract class DataCollectionDtoFactory {
    static OrderDataCollectionDto createOrderDataCollectionDto(Long userId, List<Long> itemIds, Long totalPrice) {
        return null;
    }
}

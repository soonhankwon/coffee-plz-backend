package com.soonhankwon.coffeeplzbackend.factory;

import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;

import java.util.List;

public interface DataCollectionDtoFactory {
    OrderDataCollectionDto createOrderDataCollectionDto(Long userId, List<Long> itemIds, Long totalPrice);
}

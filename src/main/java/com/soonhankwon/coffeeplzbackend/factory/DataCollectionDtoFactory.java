package com.soonhankwon.coffeeplzbackend.factory;

import com.soonhankwon.coffeeplzbackend.dto.DataCollectionDto;

import java.util.List;

public interface DataCollectionDtoFactory {
    DataCollectionDto createOrderDataCollectionDto(Long userId, List<Long> itemIds, Long totalPrice);
}

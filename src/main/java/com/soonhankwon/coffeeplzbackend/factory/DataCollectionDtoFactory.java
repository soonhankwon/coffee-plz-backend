package com.soonhankwon.coffeeplzbackend.factory;

import com.soonhankwon.coffeeplzbackend.dto.DataCollectionDto;

import java.util.List;

public abstract class DataCollectionDtoFactory {

    public DataCollectionDto createDataCollectionDto(Long userId, List<Long> itemIds, Long totalPrice) {
        return null;
    }
}

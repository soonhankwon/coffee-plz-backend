package com.soonhankwon.coffeeplzbackend.dto;

import com.soonhankwon.coffeeplzbackend.factory.DataCollectionDtoFactory;
import java.util.List;

public class DataCollectionDto extends DataCollectionDtoFactory {
    private Long userId;
    private List<Long> itemIds;
    private Long totalPrice;
    private DataCollectionDto(Long userId, List<Long> itemIds, Long totalPrice) {
        this.userId = userId;
        this.itemIds = itemIds;
        this.totalPrice = totalPrice;
    }

    @Override
    public DataCollectionDto createDataCollectionDto(Long userId, List<Long> itemIds, Long totalPrice) {
        return new DataCollectionDto(userId, itemIds, totalPrice);
    }
}

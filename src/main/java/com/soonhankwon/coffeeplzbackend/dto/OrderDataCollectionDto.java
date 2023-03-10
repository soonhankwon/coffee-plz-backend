package com.soonhankwon.coffeeplzbackend.dto;

import com.soonhankwon.coffeeplzbackend.dto.factory.DataCollectionDtoFactory;
import java.util.List;

public class OrderDataCollectionDto extends DataCollectionDtoFactory {
    private Long userId;
    private List<Long> itemIds;
    private Long totalPrice;
    private OrderDataCollectionDto(Long userId, List<Long> itemIds, Long totalPrice) {
        this.userId = userId;
        this.itemIds = itemIds;
        this.totalPrice = totalPrice;
    }
    public static OrderDataCollectionDto createOrderDataCollectionDto(Long userId, List<Long> itemIds, Long totalPrice) {
        return new OrderDataCollectionDto(userId, itemIds, totalPrice);
    }
}
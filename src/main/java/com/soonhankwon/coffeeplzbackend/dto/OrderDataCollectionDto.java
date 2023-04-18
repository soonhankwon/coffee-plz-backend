package com.soonhankwon.coffeeplzbackend.dto;

import java.util.List;

public class OrderDataCollectionDto {
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
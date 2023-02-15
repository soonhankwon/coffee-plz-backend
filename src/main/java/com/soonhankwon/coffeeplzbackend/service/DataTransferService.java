package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;

public interface DataTransferService {
    default void sendOrderData(OrderDataCollectionDto orderDataCollectionDto) {};
}

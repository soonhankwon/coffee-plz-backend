package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;

public interface DataTransferService {
    void sendOrderData(OrderDataCollectionDto orderDataCollectionDto);
}

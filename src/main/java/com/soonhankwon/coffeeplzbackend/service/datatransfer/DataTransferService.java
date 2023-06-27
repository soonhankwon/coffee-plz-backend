package com.soonhankwon.coffeeplzbackend.service.datatransfer;

import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;

public interface DataTransferService {
    default void sendOrderData(OrderDataCollectionDto orderDataCollectionDto) {};
}

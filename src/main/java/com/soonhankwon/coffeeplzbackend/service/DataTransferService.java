package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.DataCollectionDto;

public interface DataTransferService {
    void sendOrderData(DataCollectionDto dataCollectionDto);
}

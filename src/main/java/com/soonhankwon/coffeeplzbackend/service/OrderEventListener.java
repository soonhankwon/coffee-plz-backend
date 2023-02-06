package com.soonhankwon.coffeeplzbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final KafkaProducerService kafkaProducerService;
    private final DataCollectionService dataCollectionService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderService.OrderEvent event) {
        kafkaProducerService.send(event.getOrderDataCollectionDto());
        dataCollectionService.sendOrderData(event.getOrderDataCollectionDto());
    }
}

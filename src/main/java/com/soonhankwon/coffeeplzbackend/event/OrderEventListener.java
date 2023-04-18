package com.soonhankwon.coffeeplzbackend.event;

import com.soonhankwon.coffeeplzbackend.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final KafkaProducerService kafkaProducerService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderEvent event) {
        kafkaProducerService.sendOrderData(event.getOrderDataCollectionDto());
    }
}

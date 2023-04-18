package com.soonhankwon.coffeeplzbackend.event;

import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderEvent extends ApplicationEvent {
    private final OrderDataCollectionDto orderDataCollectionDto;

    public OrderEvent(Object source, OrderDataCollectionDto orderDataCollectionDto) {
        super(source);
        this.orderDataCollectionDto = orderDataCollectionDto;
    }
}

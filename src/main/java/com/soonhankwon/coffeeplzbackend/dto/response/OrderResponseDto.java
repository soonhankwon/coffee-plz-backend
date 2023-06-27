package com.soonhankwon.coffeeplzbackend.dto.response;

import com.soonhankwon.coffeeplzbackend.domain.Order;
import com.soonhankwon.coffeeplzbackend.domain.OrderStatus;
import com.soonhankwon.coffeeplzbackend.domain.OrderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private OrderType type;
    private Long totalPrice;
    private OrderStatus status;

    public OrderResponseDto (Order order) {
        this.orderId = order.getOrderId();
        this.type = order.getOrderType();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
    }
}

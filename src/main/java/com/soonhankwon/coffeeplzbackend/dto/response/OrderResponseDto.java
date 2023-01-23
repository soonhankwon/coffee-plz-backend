package com.soonhankwon.coffeeplzbackend.dto.response;

import com.soonhankwon.coffeeplzbackend.entity.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class OrderResponseDto {
    private Long orderId;
    private Order.OrderType type;
    private Long totalPrice;
    private Order.OrderStatus status;
    public OrderResponseDto (Order order) {
        this.orderId = order.getOrderId();
        this.type = order.getOrderType();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
    }
}

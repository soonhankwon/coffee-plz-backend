package com.soonhankwon.coffeeplzbackend.dto.response;

import com.soonhankwon.coffeeplzbackend.entity.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class OrderResponseDto {
    private Long orderId;
    private String type;

    private Long totalPrice;

    private String address;

    private String requirement;

    private String orderState;

    public OrderResponseDto (Order order) {
        this.orderId = order.getId();
        this.type = order.getType();
        this.totalPrice = order.getTotalPrice();
        this.address = order.getAddress();
        this.requirement = order.getRequirement();
        this.orderState = order.getOrderState();
    }
}

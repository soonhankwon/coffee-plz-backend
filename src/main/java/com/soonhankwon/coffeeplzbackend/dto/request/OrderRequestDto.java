package com.soonhankwon.coffeeplzbackend.dto.request;

import com.soonhankwon.coffeeplzbackend.entity.Order;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderRequestDto {
    private Order.OrderType orderType;

    private Long totalPrice;

    private String address;

    private String requirement;

    private String orderState;

}

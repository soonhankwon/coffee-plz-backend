package com.soonhankwon.coffeeplzbackend.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderRequestDto {
    private OrderType type;

    private Long totalPrice;

    private String address;

    private String requirement;

    private String orderState;

    public enum OrderType {
        TAKEOUT, DELIVERY
    }
}

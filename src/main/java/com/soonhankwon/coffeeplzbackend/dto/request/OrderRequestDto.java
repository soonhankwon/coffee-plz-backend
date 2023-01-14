package com.soonhankwon.coffeeplzbackend.dto.request;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private String type;

    private Long totalPrice;

    private String address;

    private String requirement;

    private String orderState;
}

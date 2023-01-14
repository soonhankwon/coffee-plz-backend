package com.soonhankwon.coffeeplzbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class OrderRequestDto {
    private String type;

    private Long totalPrice;

    private String address;

    private String requirement;

    private String orderState;
}

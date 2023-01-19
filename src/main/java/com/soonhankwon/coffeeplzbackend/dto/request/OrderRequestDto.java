package com.soonhankwon.coffeeplzbackend.dto.request;

import com.soonhankwon.coffeeplzbackend.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Order.OrderType orderType;

    private String status;

    private Long itemId;

    private Long orderItemPrice;

    private Integer quantity;

}

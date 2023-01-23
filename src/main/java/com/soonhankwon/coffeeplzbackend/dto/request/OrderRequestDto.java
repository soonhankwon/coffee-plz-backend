package com.soonhankwon.coffeeplzbackend.dto.request;

import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    private Order.OrderType orderType;

    private String status;

    private Long itemId;

    private OrderItem.ItemSize itemSize;

    private Long orderItemPrice;

    private Integer quantity;

}

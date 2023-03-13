package com.soonhankwon.coffeeplzbackend.dto.request;

import com.soonhankwon.coffeeplzbackend.domain.Order;
import com.soonhankwon.coffeeplzbackend.domain.OrderItem;
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

    private Order.OrderStatus orderStatus;

    private Long itemId;

    private OrderItem.ItemSize itemSize;

    private Long orderItemPrice;

    private Integer quantity;

    public OrderRequestDto(OrderItem.ItemSize size, Long orderItemPrice) {
        this.itemSize = size;
        this.orderItemPrice = orderItemPrice;
    }
}

package com.soonhankwon.coffeeplzbackend.dto;

import com.soonhankwon.coffeeplzbackend.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {
    private Item item;

    private Long orderItemPrice;

    private Integer quantity;

    public OrderItemDto(Item item, Long orderItemPrice, Integer quantity) {
        this.item = item;
        this.orderItemPrice = orderItemPrice;
        this.quantity = quantity;
    }
}

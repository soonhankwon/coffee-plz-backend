package com.soonhankwon.coffeeplzbackend.dto;

import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {
    private Item item;
    private Long orderItemPrice;
    private OrderItem.ItemSize itemSize;
    private Integer quantity;

    public OrderItemDto(Item item, Long orderItemPrice, OrderItem.ItemSize itemSize, Integer quantity) {
        this.item = item;
        this.itemSize = itemSize;
        this.orderItemPrice = orderItemPrice;
        this.quantity = quantity;
    }
}

package com.soonhankwon.coffeeplzbackend.dto.request;

import com.soonhankwon.coffeeplzbackend.domain.ItemSize;
import com.soonhankwon.coffeeplzbackend.domain.OrderItem;
import com.soonhankwon.coffeeplzbackend.domain.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    private OrderType orderType;

    private Long itemId;

    private ItemSize itemSize;

    private Long orderItemPrice;

    private Integer quantity;

    public OrderRequestDto(ItemSize size, Long orderItemPrice) {
        this.itemSize = size;
        this.orderItemPrice = orderItemPrice;
    }
}

package com.soonhankwon.coffeeplzbackend.dto;

import com.soonhankwon.coffeeplzbackend.domain.Item;
import com.soonhankwon.coffeeplzbackend.domain.ItemSize;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.utils.Calculator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderItemDto {
    private Item item;
    private Long orderItemPrice;
    private ItemSize itemSize;
    private Integer quantity;

    public OrderItemDto getCalculatedOrderItemDtos(Item item, OrderRequestDto dto) {
        this.item = item;
        this.itemSize = dto.getItemSize();
        this.orderItemPrice = new Calculator().calculatePriceSizeAdditionalFee(dto);
        this.quantity = dto.getQuantity();
        return this;
    }
}

package com.soonhankwon.coffeeplzbackend.dto.response;

import com.soonhankwon.coffeeplzbackend.entity.Item;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ItemResponseDto {
    private Long itemId;
    private String name;
    private Long price;

    public ItemResponseDto(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
    }
}

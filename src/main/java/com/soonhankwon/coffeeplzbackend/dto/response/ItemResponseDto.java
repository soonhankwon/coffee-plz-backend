package com.soonhankwon.coffeeplzbackend.dto.response;

import com.soonhankwon.coffeeplzbackend.entity.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ItemResponseDto {
    private String name;

    private Long price;

    public ItemResponseDto(Item item) {
        this.name = item.getName();
        this.price = item.getPrice();
    }
}

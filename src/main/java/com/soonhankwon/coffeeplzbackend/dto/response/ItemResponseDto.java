package com.soonhankwon.coffeeplzbackend.dto.response;

import com.soonhankwon.coffeeplzbackend.domain.Item;
import com.soonhankwon.coffeeplzbackend.repository.CustomItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemResponseDto {
    private Long itemId;
    private String name;
    private Integer price;

    public ItemResponseDto(Long itemId, String name, Integer price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public List<ItemResponseDto> getItemResponseDtoList(List<Item> items) {
        return items.stream()
                .map(Item::createItemResDto)
                .collect(Collectors.toList());
    }
}

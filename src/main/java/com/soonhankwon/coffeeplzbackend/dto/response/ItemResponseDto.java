package com.soonhankwon.coffeeplzbackend.dto.response;

import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.repository.CustomItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    public static List<ItemResponseDto> getItemResponseDtoList(CustomItemRepository customItemRepository, ItemRepository itemRepository) {
        List<Long> ids = customItemRepository.favoriteItems();
        List<ItemResponseDto> list = new ArrayList<>();
        for (Long id : ids) {
            Item item = itemRepository.findById(id).orElseThrow(NullPointerException::new);
            list.add(new ItemResponseDto(item));
        }
        return list;
    }
}

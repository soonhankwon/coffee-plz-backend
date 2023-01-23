package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.repository.CustomItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CustomItemRepository customItemRepository;

    public List<ItemResponseDto> findAllItem() {
        List<Item> list = itemRepository.findAll();
        return list.stream().map(ItemResponseDto::new).collect(Collectors.toList());
    }

    public ItemResponseDto findItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(NullPointerException::new);
        return new ItemResponseDto(item);
    }

    @Transactional
    public ItemResponseDto addItem(ItemRequestDto itemRequestDto) {
        Item item = Item.builder().name(itemRequestDto.getName())
                .price(itemRequestDto.getPrice())
                .build();
        itemRepository.save(item);
        return new ItemResponseDto(item);
    }

    @Transactional
    public ItemResponseDto updateItem(Long id, ItemRequestDto itemRequestDto) {
        Item item = itemRepository.findById(id).orElseThrow(NullPointerException::new);
        item.updateItem(itemRequestDto.getName(), itemRequestDto.getPrice(), itemRequestDto.getSize());
        return new ItemResponseDto(item);
    }

    @Transactional
    public GlobalResponseDto deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(NullPointerException::new);
        itemRepository.delete(item);
        return new GlobalResponseDto("삭제 완료");
    }

    @Cacheable(value="item", cacheManager = "cacheManager")
    public List<ItemResponseDto> favoriteItems() {
        System.out.println("cache ignore");
        List<Long> ids = customItemRepository.favoriteItems();
        List<ItemResponseDto> list = new ArrayList<>();
        for(Long id : ids) {
            Item item = itemRepository.findById(id).orElseThrow(NullPointerException::new);
            list.add(new ItemResponseDto(item));
        }
        return list;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @CacheEvict(value = "item", allEntries = true)
    public void deleteCache() {
        favoriteItems();
    }
}


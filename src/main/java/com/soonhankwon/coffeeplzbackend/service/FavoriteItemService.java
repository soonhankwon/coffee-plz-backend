package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.repository.CustomItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto.getItemResponseDtoList;

@Slf4j
@RequiredArgsConstructor
@Service
public class FavoriteItemService {
    private final CustomItemRepository customItemRepository;
    private final ItemRepository itemRepository;

    @CachePut(value = "item", cacheManager = "cacheManager")
    public List<ItemResponseDto> setFavoriteItemCache() {
        log.info("put cache");
        return getItemResponseDtoList(customItemRepository, itemRepository);
    }

    @CacheEvict(value = "item", allEntries = true)
    public void deleteCache() {
        log.info("delete cache");
    }
}

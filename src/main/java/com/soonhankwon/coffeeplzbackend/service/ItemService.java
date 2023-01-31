package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.repository.CustomItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto.getItemResponseDtoList;

@AllArgsConstructor
@Slf4j
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CustomItemRepository customItemRepository;
    private final FavoriteItemService favoriteItemService;
    private final RedissonClient redissonClient;

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
        item.updateItem(itemRequestDto.getName(), itemRequestDto.getPrice());
        return new ItemResponseDto(item);
    }

    @Transactional
    public GlobalResponseDto deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(NullPointerException::new);
        itemRepository.delete(item);
        return new GlobalResponseDto("삭제 완료");
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "item", cacheManager = "cacheManager")
    public List<ItemResponseDto> favoriteItems() {
        log.info("cache ignore");
        return getItemResponseDtoList(customItemRepository, itemRepository);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateFavoriteItems() {
        String lockName = "favoriteItemLock";
        RLock lock = redissonClient.getLock(lockName);
        String worker = Thread.currentThread().getName();

        try {
            boolean available = lock.tryLock(1, 10, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("Lock 을 획득하지 못했습니다.");
            }
            log.info("현재 {}서버에서 업데이트 중입니다.", worker);
            favoriteItemService.deleteCache();
            favoriteItemService.setFavoriteItemCache();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}


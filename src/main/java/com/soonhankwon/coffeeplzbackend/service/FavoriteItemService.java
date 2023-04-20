package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.domain.Item;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.repository.CustomItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FavoriteItemService {
    private final CustomItemRepository customItemRepository;
    private final ItemRepository itemRepository;
    private final RedissonClient redissonClient;

    @CachePut(value = "item", cacheManager = "cacheManager")
    public void putFavoriteItemCache() {
        log.info("put cache");
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "item", cacheManager = "cacheManager")
    public List<ItemResponseDto> getFavoriteItems() {
        log.info("cache ignore");
        List<Long> ids = findFavoriteItems();
        return new ItemResponseDto().getItemResponseDtoList(findItems(ids));
    }

    private List<Long> findFavoriteItems() {
        return customItemRepository.favoriteItems();
    }

    private List<Item> findItems(List<Long> ids) {
        return ids.stream()
                .map(id -> itemRepository.findById(id).orElseThrow(NullPointerException::new))
                .collect(Collectors.toList());
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateFavoriteItems() {
        executeUpdateWithLock(redissonClient.getLock("favoriteItemLock"));
    }

    private void executeUpdateWithLock(RLock lock) {
        try {
            boolean available = lock.tryLock(0, 10, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("Lock 을 획득하지 못했습니다.");
            }
            log.info("현재 {}서버에서 업데이트 중입니다.", Thread.currentThread().getName());
            putFavoriteItemCache();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}

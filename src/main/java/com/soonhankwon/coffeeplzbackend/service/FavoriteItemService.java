package com.soonhankwon.coffeeplzbackend.service;

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

import static com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto.getItemResponseDtoList;

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
        return getItemResponseDtoList(customItemRepository, itemRepository);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateFavoriteItems() {
        String lockName = "favoriteItemLock";
        RLock lock = redissonClient.getLock(lockName);
        String worker = Thread.currentThread().getName();

        try {
            boolean available = lock.tryLock(0, 10, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("Lock 을 획득하지 못했습니다.");
            }
            log.info("현재 {}서버에서 업데이트 중입니다.", worker);
            putFavoriteItemCache();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}

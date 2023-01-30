package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.repository.CustomItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @Mock
    ItemRepository itemRepository;
    @Mock
    CustomItemRepository customItemRepository;

    @Mock
    private RedissonClient redissonClient;

    @Mock
    private RLock lock;

    @Mock
    private Logger log;
    @Spy
    @InjectMocks
    ItemService itemService;

    @Test
    public void findAllItem() {
        //given
        List<Item> item =
                Arrays.asList(Item.builder().id(1L).name("Espresso").build(),
                        Item.builder().id(2L).name("Americano").build());

        when(itemRepository.findAll()).thenReturn(item);

        //when
        List<ItemResponseDto> result = itemService.findAllItem();

        //then
        verify(itemRepository, times(1)).findAll();
        assertThat(result.get(0).getName(), equalTo("Espresso"));
        assertThat(result.get(1).getName(), equalTo("Americano"));
    }

    @Test
    void findItem() {
        //given
        Item item = Item.builder().id(1L).name("Americano").price(3000L).build();
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        //when
        ItemResponseDto result = itemService.findItem(1L);

        //then
        assertThat(result.getName(), equalTo(item.getName()));
    }

    @Test
    void addItem() {
        //given
        ItemRequestDto itemRequestDto = new ItemRequestDto("Americano", 3000L);

        //when
        ItemResponseDto result = itemService.addItem(itemRequestDto);

        //then
        assertThat(result.getName(), equalTo("Americano"));
        assertThat(result.getPrice(), equalTo(3000L));
    }

    @Test
    @DisplayName("메뉴 업데이트 테스트")
    void updateItem() {
        //given
        Long id = 1L;
        String name1 = "Espresso";
        String name2 = "Americano";
        Long price1 = 2500L;
        Long price2 = 3000L;

        Item item = new Item(id, name1, price1);
        ItemRequestDto itemRequestDto = new ItemRequestDto(name2, price2);
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        //when
        ItemResponseDto result = itemService.updateItem(id, itemRequestDto);

        //then
        assertThat(result.getName(), equalTo("Americano"));
        assertThat(result.getPrice(), equalTo(3000L));

    }

    @Test
    @DisplayName("메뉴 삭제 테스트")
    void deleteItem() {
        //given
        Long id = 1L;
        String name = "Americano";
        Long price = 3000L;
        Item item = new Item(id, name, price);
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        //when
        GlobalResponseDto result = itemService.deleteItem(id);

        //then
        verify(itemRepository, times(1)).delete(item);
        assertThat(result.getMessage(), equalTo("삭제 완료"));
    }

    @Test
    void testUpdateFavoriteItems() throws InterruptedException {
        // given
        when(redissonClient.getLock("favoriteItemLock")).thenReturn(lock);
        when(lock.tryLock(1, 1, TimeUnit.SECONDS)).thenReturn(true);
        List<Long> ids = Arrays.asList(1L,2L,3L);
        List<Item> list = Arrays.asList(Item.builder().id(1L).price(2000L).name("Americano").build(),
                Item.builder().id(2L).price(2500L).name("Latte").build(),
                Item.builder().id(1L).price(2000L).name("Americano").build());

        // when
        when(customItemRepository.favoriteItems()).thenReturn(ids);
        when(itemRepository.findById(1L)).thenReturn(Optional.ofNullable(list.get(0)));
        when(itemRepository.findById(2L)).thenReturn(Optional.ofNullable(list.get(1)));
        when(itemRepository.findById(3L)).thenReturn(Optional.ofNullable(list.get(2)));
        itemService.updateFavoriteItems();

        // then
//        verify(lock).unlock();
        verify(itemService).favoriteItems();
    }
}
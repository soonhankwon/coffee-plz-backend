package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @Mock
    ItemRepository itemRepository;

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
        itemRepository.save(item);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        //when
        ItemResponseDto result = itemService.findItem(1L);

        //then
        verify(itemRepository, times(1)).findById(any());
        assertThat(result.getName(), equalTo(item.getName()));
    }

    @Test
    void addItem() {
        //given
        ItemRequestDto itemRequestDto = new ItemRequestDto("Americano", "Small", 2500L);
        Item item = Item.builder().id(1L)
                .name(itemRequestDto.getName())
                .size(itemRequestDto.getSize())
                .price(itemRequestDto.getPrice())
                .build();

        when(itemRepository.save(any())).thenReturn(item);

        //when
        ItemResponseDto result = itemService.addItem(itemRequestDto);

        //then
        assertThat(result.getName(), equalTo("Americano"));
        assertThat(result.getSize(), equalTo("Small"));
        assertThat(result.getPrice(), equalTo(2500L));
    }

    @Test
    @DisplayName("메뉴 업데이트 테스트")
    void updateItem() {
        //given
        Long id = 1L;
        String name = "Espresso";
        String name2 = "Americano";
        String size = "Small";
        Long price = 3000L;
        ItemRequestDto itemRequestDto = new ItemRequestDto(name, size, price);
        Item item = new Item(id, name2, size, price);
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        //when
        ItemResponseDto result = itemService.updateItem(id, itemRequestDto);

        //then
        assertThat(result.getName(), equalTo("Espresso"));
        assertThat(result.getSize(), equalTo("Small"));
        assertThat(result.getPrice(), equalTo(3000L));

    }

    @Test
    @DisplayName("메뉴 삭제 테스트")
    void deleteItem() {
        //given
        Long id = 1L;
        String name = "Americano";
        String size = "Small";
        Long price = 3000L;
        Item item = new Item(id, name, size, price);
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        //when
        GlobalResponseDto result = itemService.deleteItem(id);

        //then
        assertThat(result.getMessage(), equalTo("삭제 완료"));
    }
}
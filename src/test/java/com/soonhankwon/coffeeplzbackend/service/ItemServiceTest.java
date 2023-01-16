package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
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
}
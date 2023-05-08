package com.soonhankwon.coffeeplzbackend.repository;

import com.soonhankwon.coffeeplzbackend.domain.Item;
import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRepositoryTest {

    @Mock
    ItemRepository itemRepository;

    @Test
    void 아이템_전체조회() {
        Item item1 = new Item(new ItemRequestDto("espresso", 2000));
        Item item2 = new Item(new ItemRequestDto("americano", 2500));

        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));
        List<Item> result = itemRepository.findAll();

        Assertions.assertEquals(2, result.size());
    }
}
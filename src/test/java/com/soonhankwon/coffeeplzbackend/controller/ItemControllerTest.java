package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import com.soonhankwon.coffeeplzbackend.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {
    @Autowired
    MockMvc mvc;

    @Mock
    ItemRepository itemRepository;

    @MockBean
    ItemService itemService;

    @Test
    @DisplayName("커피 메뉴 목록 조회 테스트")
    void findAllItem() throws Exception {
        List<Item> item = new ArrayList<>();
        item.add(Item.builder().name("Americano").price(3000).build());
        item.add(Item.builder().name("CafeLatte").price(3000).build());
        List<ItemResponseDto> list = item.stream().map(ItemResponseDto::new).collect(Collectors.toList());

        given(itemService.findAllItem()).willReturn(list);

        //then
        mvc.perform(get("/item"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Americano")))
                .andExpect(content().string(containsString("CafeLatte")));
    }

    @Test
    @DisplayName("커피 메뉴 조회 테스트")
    void findItem() throws Exception {
        //given
        Item item = Item.builder().id(1L).name("Americano").price(3000).build();
        itemRepository.save(item);
        ItemResponseDto result = new ItemResponseDto(item);

        given(itemService.findItem(item.getId())).willReturn(result);
        //then
        mvc.perform(get("/item/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Americano")));

    }
}
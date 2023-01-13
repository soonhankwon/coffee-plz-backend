package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.entity.Menu;
import com.soonhankwon.coffeeplzbackend.repository.MenuRepository;
import com.soonhankwon.coffeeplzbackend.service.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuController.class)
class MenuControllerTest {
    @Autowired
    MockMvc mvc;

    @Mock
    MenuRepository menuRepository;

    @MockBean
    MenuService menuService;

    @Test
    @DisplayName("커피 메뉴 목록 조회 테스트")
    void findAllMenu() throws Exception {
        List<Menu> menu = new ArrayList<>();
        menu.add(Menu.builder().name("Americano").price(3000L).build());
        menu.add(Menu.builder().name("CafeLatte").price(3000L).build());

        given(menuService.findAllMenu()).willReturn(menu);

        //then
        mvc.perform(get("/menu"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Americano")))
                .andExpect(content().string(containsString("CafeLatte")));
    }

    @Test
    @DisplayName("커피 메뉴 조회 테스트")
    void findMenu() throws Exception {
        //given
        Menu menu = Menu.builder().id(1L).name("Americano").price(3000L).build();
        menuRepository.save(menu);

        given(menuService.findMenu(menu.getId())).willReturn(menu);
        //then
        mvc.perform(get("/menu/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Americano")));

    }
}
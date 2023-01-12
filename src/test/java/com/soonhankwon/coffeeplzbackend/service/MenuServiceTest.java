package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.entity.Menu;
import com.soonhankwon.coffeeplzbackend.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    @Mock
    MenuRepository menuRepository;

    @InjectMocks
    MenuService menuService;

    @Test
    public void findAllMenu() {
        //given
        List<Menu> menu =
                Arrays.asList(Menu.builder().id(1L).name("Espresso").build(),
                        Menu.builder().id(2L).name("Americano").build());

        when(menuRepository.findAll()).thenReturn(menu);

        //when
        List<Menu> result = menuService.findAllMenu();

        //then
        verify(menuRepository, times(1)).findAll();
        assertThat(result, equalTo(menu));
        assertThat(result.get(0).getName(), equalTo("Espresso"));
        assertThat(result.get(1).getName(), equalTo("Americano"));
    }
}
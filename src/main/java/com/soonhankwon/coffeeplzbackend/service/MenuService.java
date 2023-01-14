package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.MenuResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Menu;
import com.soonhankwon.coffeeplzbackend.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuResponseDto> findAllMenu() {
        List<Menu> list = menuRepository.findAll();
        return list.stream().map(MenuResponseDto::new).collect(Collectors.toList());
    }

    public MenuResponseDto findMenu(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(NullPointerException::new);
        return new MenuResponseDto(menu);
    }
}

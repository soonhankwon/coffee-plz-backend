package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.entity.Menu;
import com.soonhankwon.coffeeplzbackend.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public List<Menu> findAllMenu() {
        return menuRepository.findAll();
    }

    public Menu findMenu(Long id) {

        return menuRepository.findById(id).orElseThrow(NullPointerException::new);
    }
}

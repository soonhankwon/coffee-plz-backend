package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Menu;
import com.soonhankwon.coffeeplzbackend.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/menu")
    public ResponseEntity<List<Menu>> findAllMenu() {
        return ResponseEntity.ok(menuService.findAllMenu());
    }
    @GetMapping("/menu/{id}")
    public ResponseEntity<Menu> findMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.findMenu(id));
    }
}
package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.MenuResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Menu;
import com.soonhankwon.coffeeplzbackend.service.MenuService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = "커피 메뉴 조회 API")
public class MenuController {
    private final MenuService menuService;
    @GetMapping("/menu")
    public ResponseEntity<List<MenuResponseDto>> findAllMenuTest() {
        return ResponseEntity.status(200).body(menuService.findAllMenu());
    }
    @GetMapping("/menu/{id}")
    public ResponseEntity<Menu> findMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.findMenu(id));
    }


}

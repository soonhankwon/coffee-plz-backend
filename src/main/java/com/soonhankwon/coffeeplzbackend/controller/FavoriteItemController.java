package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.service.FavoriteItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FavoriteItemController {
    private final FavoriteItemService favoriteItemService;

    @GetMapping("/item/favorite")
    @CrossOrigin(origins = "*")
    @Operation(summary = "7일간 인기메뉴 조회")
    public ResponseEntity<List<ItemResponseDto>> favoriteItems() {
        return ResponseEntity.status(200).body(favoriteItemService.getFavoriteItems());
    }
    @GetMapping("/item/favorite/test")
    @Operation(summary = "인기메뉴 조회 테스트")
    public void favoriteItemsTest() {
        favoriteItemService.updateFavoriteItems();
    }
}

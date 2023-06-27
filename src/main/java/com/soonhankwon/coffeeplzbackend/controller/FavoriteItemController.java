package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.service.FavoriteItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FavoriteItemController {

    private final FavoriteItemService favoriteItemService;

    @GetMapping("/items/favorite")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    @Operation(summary = "7일간 인기메뉴 조회")
    public List<ItemResponseDto> favoriteItems() {
        return favoriteItemService.getFavoriteItems();
    }

    @GetMapping("/items/favorite/test")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "인기메뉴 조회 테스트")
    public void favoriteItemsTest() {
        favoriteItemService.updateFavoriteItems();
    }
}

package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.service.FavoriteItemService;
import com.soonhankwon.coffeeplzbackend.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "커피 메뉴 조회 및 업데이트 API")
public class ItemController {
    private final ItemService regularItemService;

    @GetMapping("/item")
    @Operation(summary = "전체 메뉴 조회")
    public ResponseEntity<List<ItemResponseDto>> findAllItem() {
        return ResponseEntity.status(200).body(regularItemService.findAllItem());
    }
    @GetMapping("/item/{itemId}")
    @Operation(summary = "단일 메뉴 검색")
    public ResponseEntity<ItemResponseDto> findItem(@PathVariable Long itemId) {
        return ResponseEntity.status(200).body(regularItemService.findItem(itemId));
    }

    @PostMapping("/item/add")
    @Operation(summary = "메뉴 추가")
    public ResponseEntity<ItemResponseDto> addItem(ItemRequestDto itemRequestDto) {
        return ResponseEntity.status(200).body(regularItemService.addItem(itemRequestDto));
    }

    @PatchMapping("/item/{itemId}")
    @Operation(summary = "메뉴 업데이트")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, ItemRequestDto itemRequestDto) {
        return ResponseEntity.status(200).body(regularItemService.updateItem(itemId, itemRequestDto));
    }

    @DeleteMapping("/item/delete/{itemId}")
    @Operation(summary = "메뉴 삭제")
    public ResponseEntity<GlobalResponseDto> deleteItem(@PathVariable Long itemId) {
        return ResponseEntity.status(200).body(regularItemService.deleteItem(itemId));
    }
}

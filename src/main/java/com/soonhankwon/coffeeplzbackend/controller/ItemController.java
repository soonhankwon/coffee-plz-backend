package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.service.ItemService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = "커피 메뉴 조회 및 업데이트 API")
public class ItemController {
    private final ItemService itemService;
    @GetMapping("/item")
    public ResponseEntity<List<ItemResponseDto>> findAllItem() {
        return ResponseEntity.status(200).body(itemService.findAllItem());
    }
    @GetMapping("/item/{itemId}")
    public ResponseEntity<ItemResponseDto> findItem(@PathVariable Long itemId) {
        return ResponseEntity.status(200).body(itemService.findItem(itemId));
    }

    @PostMapping("/item/add")
    public ResponseEntity<ItemResponseDto> addItem(ItemRequestDto itemRequestDto) {
        return ResponseEntity.status(200).body(itemService.addItem(itemRequestDto));
    }

    @PatchMapping("/item/{itemId}")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, ItemRequestDto itemRequestDto) {
        return ResponseEntity.status(200).body(itemService.updateItem(itemId, itemRequestDto));
    }

    @DeleteMapping("/item/delete/{itemId}")
    public ResponseEntity<GlobalResponseDto> deleteItem(@PathVariable Long itemId) {
        return ResponseEntity.status(200).body(itemService.deleteItem(itemId));
    }
}

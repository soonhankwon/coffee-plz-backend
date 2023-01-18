package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.service.ItemService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/item/{id}")
    public ResponseEntity<ItemResponseDto> findItem(@PathVariable Long id) {
        return ResponseEntity.status(200).body(itemService.findItem(id));
    }

    @PostMapping("/item/add")
    public ResponseEntity<ItemResponseDto> addItem(ItemRequestDto itemRequestDto) {
        return ResponseEntity.status(200).body(itemService.addItem(itemRequestDto));
    }
}

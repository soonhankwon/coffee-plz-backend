package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;

import java.util.List;

public interface ItemService {
    List<ItemResponseDto> findAllItem();
    ItemResponseDto findItem(Long id);
    ItemResponseDto addItem(ItemRequestDto itemRequestDto);
    ItemResponseDto updateItem(Long id, ItemRequestDto itemRequestDto);
    GlobalResDto deleteItem(Long id);
}

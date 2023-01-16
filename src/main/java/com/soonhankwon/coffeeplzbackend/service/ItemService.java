package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public List<ItemResponseDto> findAllItem() {
        List<Item> list = itemRepository.findAll();
        return list.stream().map(ItemResponseDto::new).collect(Collectors.toList());
    }

    public ItemResponseDto findItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(NullPointerException::new);
        return new ItemResponseDto(item);
    }
}

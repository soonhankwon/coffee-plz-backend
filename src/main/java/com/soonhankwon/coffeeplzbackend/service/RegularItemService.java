package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.domain.Item;
import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RegularItemService implements ItemService {
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<ItemResponseDto> findAllItem() {
        List<Item> list = itemRepository.findAll();
        return list.stream()
                .map(Item::createItemResDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemResponseDto findItem(Long id) {
        Item item = getItemExistsOrThrowException(id);
        return item.createItemResDto();
    }

    @Transactional
    public ItemResponseDto addItem(ItemRequestDto itemRequestDto) {
        Item item = new Item(itemRequestDto);
        itemRepository.save(item);
        return item.createItemResDto();
    }

    @Transactional
    public ItemResponseDto updateItem(Long id, ItemRequestDto itemRequestDto) {
        Item item = getItemExistsOrThrowException(id);
        item.updateItemWithValidPrice(itemRequestDto);
        return item.createItemResDto();
    }

    @Transactional
    public GlobalResponseDto deleteItem(Long id) {
        itemRepository.delete(getItemExistsOrThrowException(id));
        return new GlobalResponseDto("삭제완료");
    }

    private Item getItemExistsOrThrowException(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new RequestException(ErrorCode.ITEM_NOT_FOUND));
    }
}


package com.soonhankwon.coffeeplzbackend.dto.response;

import com.soonhankwon.coffeeplzbackend.domain.ItemSize;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderSheetResDto {
    private final List<String> itemNames;
    private final List<ItemSize> itemSizes;
    private final List<Integer> itemQuantities;
    private final List<Long> itemPrices;
    private final Long totalPrices;

    public OrderSheetResDto(List<String> itemNames, List<ItemSize> itemSizes, List<Integer> itemQuantities, List<Long> itemPrices, Long totalPrices) {
        this.itemNames = itemNames;
        this.itemSizes = itemSizes;
        this.itemQuantities = itemQuantities;
        this.itemPrices = itemPrices;
        this.totalPrices = totalPrices;
    }
}

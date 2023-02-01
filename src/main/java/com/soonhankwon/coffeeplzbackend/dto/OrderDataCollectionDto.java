package com.soonhankwon.coffeeplzbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDataCollectionDto {
    private Long userId;
    private List<Long> itemId;
    private Long totalPrice;
}

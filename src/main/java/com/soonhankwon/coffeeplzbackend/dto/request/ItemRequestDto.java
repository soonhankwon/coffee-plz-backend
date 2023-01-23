package com.soonhankwon.coffeeplzbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRequestDto {
    private String name;

    private Long price;
}

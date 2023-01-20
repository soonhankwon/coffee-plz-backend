package com.soonhankwon.coffeeplzbackend.dto.response;

import lombok.Getter;

@Getter
public class FavoriteItemResponseDto {
    private final String name;

    public FavoriteItemResponseDto (String name) {
        this.name = name;
    }
}

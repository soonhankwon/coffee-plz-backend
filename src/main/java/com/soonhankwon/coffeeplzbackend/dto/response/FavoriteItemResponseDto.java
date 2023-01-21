package com.soonhankwon.coffeeplzbackend.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteItemResponseDto {
    private String name;

    public FavoriteItemResponseDto (String name) {
        this.name = name;
    }
}

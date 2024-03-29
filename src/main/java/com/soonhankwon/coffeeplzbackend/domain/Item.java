package com.soonhankwon.coffeeplzbackend.domain;

import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.dto.request.ItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.ItemResponseDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    private static final int MIN_PRICE = 0;

    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public Item(ItemRequestDto dto) {
        this.name = dto.getName();
        this.price = dto.getPrice();
    }

    public ItemResponseDto createItemResDto() {
        return new ItemResponseDto(this.id, this.name, this.price);
    }

    public void updateItemWithValidPrice(ItemRequestDto dto) {
        if (price < MIN_PRICE) {
            throw new RequestException(ErrorCode.ITEM_UPDATE_PRICE_INVALID);
        }
        this.name = dto.getName();
        this.price = dto.getPrice();
    }

    public String getName() {
        return this.name;
    }
}

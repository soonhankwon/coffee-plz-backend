package com.soonhankwon.coffeeplzbackend.domain;

import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
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

    public void updateItemWithValidPrice(String name, Integer price) {
        if (price < MIN_PRICE) {
            throw new RequestException(ErrorCode.ITEM_UPDATE_PRICE_INVALID);
        }
        this.name = name;
        this.price = price;
    }
}

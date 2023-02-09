package com.soonhankwon.coffeeplzbackend.entity;

import com.soonhankwon.coffeeplzbackend.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.exception.RequestException;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Integer price;

    public void updateItemWithValidPrice(String name, Integer price) {
        if (price > 0) {
            this.name = name;
            this.price = price;
        } else {
            throw new RequestException(ErrorCode.ITEM_UPDATE_PRICE_INVALID);
        }
    }
}

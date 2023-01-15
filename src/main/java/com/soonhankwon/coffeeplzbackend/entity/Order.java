package com.soonhankwon.coffeeplzbackend.entity;


import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@TableGenerator(
        name = "ORDER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "ORDER_SEQ", allocationSize = 50)
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ORDER_SEQ_GENERATOR")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderRequestDto.OrderType type;

    @Column(nullable = false)
    private Long totalPrice;

    @Column
    private String address;

    @Column
    private String requirement;

    @Column(nullable = false)
    private String orderState;

    public Order(OrderRequestDto orderRequestDto) {
        this.type = orderRequestDto.getType();
        this.totalPrice = orderRequestDto.getTotalPrice();
        this.address = orderRequestDto.getAddress();
        this.requirement = orderRequestDto.getRequirement();
        this.orderState = orderRequestDto.getOrderState();
    }
    public enum OrderType {
        TAKEOUT, DELIVERY
    }
}

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
@Table(name = "order")
public class Order extends BaseTimeEntity {
    @Id
    @Column(name = "order_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "requirement", nullable = false)
    private String requirement;

    @Column(name = "order_state", nullable = false)
    private String orderState;

    public enum OrderType {
        TAKEOUT, DELIVERY
    }

    public Order(OrderRequestDto orderRequestDto) {
        this.orderType = orderRequestDto.getOrderType();
        this.totalPrice = orderRequestDto.getTotalPrice();
        this.address = orderRequestDto.getAddress();
        this.requirement = orderRequestDto.getRequirement();
        this.orderState = orderRequestDto.getOrderState();
    }
}

package com.soonhankwon.coffeeplzbackend.entity;


import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order {
    @Id
    @Column(name = "order_id")
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

    @Column(name = "status", nullable = false)
    private String status;

    public enum OrderType {
        TAKEOUT, STORE
    }

    public Order(OrderRequestDto orderRequestDto) {
        this.orderType = orderRequestDto.getOrderType();
        this.totalPrice = orderRequestDto.getOrderPrice();
        this.status = orderRequestDto.getStatus();
    }
}

package com.soonhankwon.coffeeplzbackend.entity;


import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "`order`")
public class Order extends BaseTimeEntity {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public enum OrderType {
        TAKEOUT, STORE
    }

    public enum OrderStatus {
        READY, ORDERED, PAID
    }

    public void setOrderStatus(OrderStatus status) {
        this.status = status;
    }
}

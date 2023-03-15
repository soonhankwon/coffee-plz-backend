package com.soonhankwon.coffeeplzbackend.domain;


import com.soonhankwon.coffeeplzbackend.common.domain.BaseTimeEntity;
import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.utils.Calculator;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`order`", indexes = @Index(name = "idx_user_id", columnList = "user_id"))
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setStatusPaid() {
        if(isStatusOrdered()) {
            this.status = OrderStatus.PAID;
        }
    }

    private boolean isStatusOrdered() {
        if(this.status != OrderStatus.ORDERED) {
            throw new RequestException(ErrorCode.ORDER_STATUS_INVALID);
        }
        return true;
    }

    public static Order createOrder(User user, List<OrderRequestDto> orderRequests, long totalPrice, List<OrderItemDto> orderItemDtoList) {
        if (orderRequests == null || orderRequests.isEmpty()) {
            throw new RequestException(ErrorCode.EMPTY_ORDER_LIST);
        }
        Order order = new Order();
        order.orderType = orderRequests.get(0).getOrderType();
        order.totalPrice = totalPrice;
        order.status = OrderStatus.ORDERED;
        order.user = user;
        order.orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtoList) {
            order.orderItems.add(new OrderItem(orderItemDto, order));
        }
        return order;
    }
}
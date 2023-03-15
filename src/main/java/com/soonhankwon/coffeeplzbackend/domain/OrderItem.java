package com.soonhankwon.coffeeplzbackend.domain;

import com.soonhankwon.coffeeplzbackend.common.domain.BaseTimeEntity;
import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderSheetResDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_item", indexes = {
        @Index(name = "idx_order_id", columnList = "order_id"),
        @Index(name = "idx_item_id", columnList = "item_id")})
public class OrderItem extends BaseTimeEntity {
    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "item_size", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemSize itemSize;

    @Column(name = "order_item_price", nullable = false)
    private Long orderItemPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public OrderItem(OrderItemDto orderItemDto, Order order) {
        this.orderItemPrice = orderItemDto.getOrderItemPrice();
        this.itemSize = orderItemDto.getItemSize();
        this.quantity = orderItemDto.getQuantity();
        this.item = orderItemDto.getItem();
        this.order = order;
    }

    public static OrderSheetResDto createOrderSheet (List<OrderItem> orderItems) {
        List<String> itemNames = orderItems.stream()
                .map(orderItem -> orderItem.item.getName())
                .collect(Collectors.toList());

        List<ItemSize> itemSizes = orderItems.stream()
                .map(orderItem -> orderItem.itemSize)
                .collect(Collectors.toList());

        List<Integer> itemQuantities = orderItems.stream()
                .map(orderItem -> orderItem.quantity)
                .collect(Collectors.toList());

        List<Long> itemPrices = orderItems.stream()
                .map(orderItem -> orderItem.orderItemPrice)
                .collect(Collectors.toList());

        long totalPrice = orderItems.stream()
                .mapToLong(orderItem -> orderItem.quantity * orderItem.orderItemPrice)
                .sum();

        return new OrderSheetResDto(itemNames, itemSizes, itemQuantities, itemPrices, totalPrice);
    }
}

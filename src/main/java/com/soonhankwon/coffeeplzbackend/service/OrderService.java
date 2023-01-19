package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.entity.OrderItem;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.OrderItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    public List<OrderResponseDto> findAllOrder() {
        List<Order> list = orderRepository.findAll();
        return list.stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

    public OrderResponseDto orderProcessing(Long userId, List<OrderRequestDto> orderRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);

        List<OrderItemDto> orderItemList = new ArrayList<>();
        for (OrderRequestDto dto : orderRequestDto) {
            Item item = getItem(dto.getItemId());
            orderItemList.add(new OrderItemDto(item, dto.getOrderItemPrice(), dto.getQuantity()));
        }

        long totalPrice = 0;
        for(OrderItemDto j : orderItemList) {
            totalPrice += j.getOrderItemPrice() * j.getQuantity();
        }

        if(user.getPoint() < totalPrice)
            throw new RuntimeException("포인트가 부족합니다.");

        Order order = Order.builder().orderType(orderRequestDto.get(0).getOrderType())
                .totalPrice(totalPrice)
                .status("주문완료")
                .user(user)
                .build();

        orderRepository.save(order);

        for(OrderItemDto dto : orderItemList) {
            OrderItem orderItem = OrderItem.builder().order(order)
                    .item(dto.getItem())
                    .orderItemPrice(dto.getOrderItemPrice())
                    .quantity(dto.getQuantity())
                    .build();
            orderItemRepository.save(orderItem);
        }

        return new OrderResponseDto(order);
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(()
                -> new NullPointerException("NO ITEM"));
    }
}

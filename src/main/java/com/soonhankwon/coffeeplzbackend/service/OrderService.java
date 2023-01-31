package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.entity.OrderItem;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
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

    public List<OrderResponseDto> findAllOrders() {
        return orderRepository.findAll().stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

    public OrderResponseDto orderProcessing(Long userId, List<OrderRequestDto> orderRequestDto) {

        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);

        boolean previousOrderExists = orderRepository.existsByUserIdAndStatus(userId, Order.OrderStatus.ORDERED);
        if(previousOrderExists) {
            throw new IllegalStateException("이전 주문 결제 후 주문이 가능합니다.");
        }

        List<OrderItemDto> orderItemList = new ArrayList<>();
        for (OrderRequestDto dto : orderRequestDto) {
            Item item = getItem(dto.getItemId());
            Long price = OrderItem.calculatePrice(dto);
            orderItemList.add(new OrderItemDto(item, price, dto.getItemSize(), dto.getQuantity()));
        }

        long totalPrice = Order.calculateTotalPrice(orderItemList);
        Order order = Order.createOrder(user, orderRequestDto, totalPrice, orderItemList);
        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(()
                -> new NullPointerException("NO ITEM"));
    }
}

package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;
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
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final DataCollectionService dataCollectionService;
    private final RedissonClient redissonClient;
    private final TransactionService transactionService;

    public List<OrderResponseDto> findAllOrders() {
        return orderRepository.findAll().stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }
    public OrderResponseDto orderProcessingWithLock(Long userId, List<OrderRequestDto> orderRequestDto) {
        RLock lock = redissonClient.getLock(String.valueOf(userId));
        String worker = Thread.currentThread().getName();
        OrderResponseDto orderResponseDto;

        try {
            boolean available = lock.tryLock(0, 2, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("Lock 을 획득하지 못했습니다.");
            }
            log.info("현재 {}서버에서 작업중입니다.", worker);
            orderResponseDto = transactionService.executeAsTransactional(() -> orderProcessing(userId, orderRequestDto));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return orderResponseDto;
    }
    public OrderResponseDto orderProcessing(Long userId, List<OrderRequestDto> orderRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(NullPointerException::new);
        boolean previousOrderExists = orderRepository.existsByUserIdAndStatus(userId, Order.OrderStatus.ORDERED);
        if (previousOrderExists) {
            throw new IllegalStateException("이전 주문 결제 후 주문이 가능합니다.");
        }

        List<Long> itemIds = new ArrayList<>();
        List<OrderItemDto> orderItemList = new ArrayList<>();
        for (OrderRequestDto dto : orderRequestDto) {
            Item item = getItem(dto.getItemId());
            itemIds.add(dto.getItemId());
            Long price = OrderItem.calculatePrice(dto);
            orderItemList.add(new OrderItemDto(item, price, dto.getItemSize(), dto.getQuantity()));
        }

        long totalPrice = Order.calculateTotalPrice(orderItemList);
        Order order = Order.createOrder(user, orderRequestDto, totalPrice, orderItemList);
        orderRepository.save(order);

        OrderDataCollectionDto orderDataCollectionDto = new OrderDataCollectionDto(userId, itemIds, totalPrice);
        dataCollectionService.sendOrderData(orderDataCollectionDto);

        return new OrderResponseDto(order);
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(()
                -> new NullPointerException("NO ITEM"));
    }
}

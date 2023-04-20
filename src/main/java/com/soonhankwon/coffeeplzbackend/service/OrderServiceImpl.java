package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.domain.Item;
import com.soonhankwon.coffeeplzbackend.domain.Order;
import com.soonhankwon.coffeeplzbackend.domain.OrderStatus;
import com.soonhankwon.coffeeplzbackend.domain.User;
import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;
import com.soonhankwon.coffeeplzbackend.dto.OrderDto;
import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.event.OrderEvent;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final RedissonClient redissonClient;
    private final TransactionService transactionService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAllOrders() {
        return orderRepository.findAll().stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

    public OrderResponseDto placeOrder(OrderDto orderDto) {
        return executeOrderWithLock(orderDto);
    }

    private OrderResponseDto executeOrderWithLock(OrderDto orderDto) {
        RLock lock = redissonClient.getLock(String.valueOf(orderDto.getUserId()));
        String worker = Thread.currentThread().getName();
        OrderResponseDto orderResponseDto;
        try {
            boolean available = lock.tryLock(0, 2, TimeUnit.SECONDS);
            if (!available) {
                throw new RuntimeException("Lock 을 획득하지 못했습니다.");
            }
            log.info("현재 {}서버에서 작업중입니다.", worker);
            orderResponseDto = transactionService.executeAsTransactional(() -> orderProcessing(orderDto));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return orderResponseDto;
    }

    public OrderResponseDto orderProcessing(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(
                () -> new RequestException(ErrorCode.USER_NOT_FOUND));
        validateOrderStatus(orderDto.getUserId());

        List<Long> itemIds = getItemIds(orderDto);
        List<OrderItemDto> orderItems = getOrderItemDtos(orderDto);

        Order order = new Order(user, orderDto.getOrderRequestDto(), orderItems);
        orderRepository.save(order);
        applicationEventPublisher.publishEvent(new OrderEvent(this, new OrderDataCollectionDto(orderDto.getUserId(), itemIds, order.getTotalPrice())));
        return new OrderResponseDto(order);
    }

    private List<Long> getItemIds(OrderDto orderDto) {
        return orderDto.getOrderRequestDto().stream()
                .map(OrderRequestDto::getItemId)
                .collect(Collectors.toList());
    }

    private List<OrderItemDto> getOrderItemDtos(OrderDto orderDto) {
        return orderDto.getOrderRequestDto().stream()
                .map(dto -> {
                    Item item = itemRepository.findById(dto.getItemId()).orElseThrow(
                            () -> new RequestException(ErrorCode.ITEM_NOT_FOUND));
                    return new OrderItemDto().getCalculatedOrderItemDtos(item, dto);
                })
                .collect(Collectors.toList());
    }

    private void validateOrderStatus(Long userId) {
        if (orderRepository.existsByUserIdAndStatus(userId, OrderStatus.ORDERED))
            throw new RequestException(ErrorCode.PREVIOUS_ORDER_EXISTS);
    }
}

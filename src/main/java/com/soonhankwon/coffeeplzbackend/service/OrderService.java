package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;
import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.domain.Item;
import com.soonhankwon.coffeeplzbackend.domain.Order;
import com.soonhankwon.coffeeplzbackend.domain.OrderItem;
import com.soonhankwon.coffeeplzbackend.domain.User;
import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.dto.factory.DataCollectionDtoFactory;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final RedissonClient redissonClient;
    private final TransactionService transactionService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAllOrders() {
        return orderRepository.findAll().stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }
    public OrderResponseDto placeOrder(Long userId, List<OrderRequestDto> orderRequestDto) {
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
        User user = getUserExistsOrThrowException(userId);
        checkForPreviousOrder(userId);

        List<Long> itemIds = orderRequestDto.stream()
                .map(OrderRequestDto::getItemId)
                .collect(Collectors.toList());

        List<OrderItemDto> orderItemList = orderRequestDto.stream()
                .map(dto -> {
                    Item item = getItemExistsOrThrowException(dto.getItemId());
                    Long price = OrderItem.calculatePrice(dto);
                    return new OrderItemDto(item, price, dto.getItemSize(),dto.getQuantity());})
                .collect(Collectors.toList());

        long totalPrice = Order.calculateTotalPrice(orderItemList);
        Order order = Order.createOrder(user, orderRequestDto, totalPrice, orderItemList);
        orderRepository.save(order);
        eventPublisher.publishEvent(new OrderEvent(DataCollectionDtoFactory.createOrderDataCollectionDto(userId, itemIds, totalPrice)));
        return new OrderResponseDto(order);
    }

    private boolean isPreviousOrderExist(Long userId) {
        return orderRepository.existsByUserIdAndStatus(userId, Order.OrderStatus.ORDERED);
    }

    private void checkForPreviousOrder(Long userId) {
        if(isPreviousOrderExist(userId))
            throw new RequestException(ErrorCode.PREVIOUS_ORDER_EXISTS);
    }

    private Item getItemExistsOrThrowException(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new RequestException(ErrorCode.ITEM_NOT_FOUND));
    }
    private User getUserExistsOrThrowException(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RequestException(ErrorCode.USER_NOT_FOUND));
    }

    public static class OrderEvent {
        @Getter
        private final OrderDataCollectionDto orderDataCollectionDto;
        public OrderEvent(OrderDataCollectionDto orderDataCollectionDto) {
            this.orderDataCollectionDto = orderDataCollectionDto;
        }
    }
}

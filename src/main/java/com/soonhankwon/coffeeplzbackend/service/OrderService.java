package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.domain.*;
import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;
import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderSheetResDto;
import com.soonhankwon.coffeeplzbackend.event.OrderEvent;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import com.soonhankwon.coffeeplzbackend.utils.Calculator;
import lombok.AllArgsConstructor;
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
    private final Calculator calculator;

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
    protected OrderResponseDto orderProcessing(Long userId, List<OrderRequestDto> orderRequestDto) {
        User user = getUserExistsOrThrowException(userId);
        checkForPreviousOrder(userId);

        List<Long> itemIds = orderRequestDto.stream()
                .map(OrderRequestDto::getItemId)
                .collect(Collectors.toList());

        List<OrderItemDto> orderItemList = orderRequestDto.stream()
                .map(dto -> {
                    Item item = getItemExistsOrThrowException(dto.getItemId());
                    Long price = calculator.calculatePriceSizeAdditionalFee(dto);
                    return new OrderItemDto(item, price, dto.getItemSize(),dto.getQuantity());})
                .collect(Collectors.toList());

        long totalPrice = calculator.calculateTotalPrice(orderItemList);
        Order order = Order.createOrder(user, orderRequestDto, totalPrice, orderItemList);
        orderRepository.save(order);
        eventPublisher.publishEvent(new OrderEvent(this, OrderDataCollectionDto.createOrderDataCollectionDto(userId, itemIds, totalPrice)));
        return new OrderResponseDto(order);
    }

    @Transactional(readOnly = true)
    public OrderSheetResDto findOrderSheet(Long userId) {
        User user = getUserExistsOrThrowException(userId);
        Order order = orderRepository.findOrderByUserAndStatus(user, OrderStatus.ORDERED).orElseThrow(
                () -> new RequestException(ErrorCode.ORDER_NOT_FOUND));
        List<OrderItem> orderItems = order.getOrderItems();
        return OrderItem.createOrderSheet(orderItems);
    }

    private boolean isPreviousOrderExist(Long userId) {
        return orderRepository.existsByUserIdAndStatus(userId, OrderStatus.ORDERED);
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
}
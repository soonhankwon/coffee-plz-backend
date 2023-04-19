package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.domain.Item;
import com.soonhankwon.coffeeplzbackend.domain.Order;
import com.soonhankwon.coffeeplzbackend.domain.OrderStatus;
import com.soonhankwon.coffeeplzbackend.domain.User;
import com.soonhankwon.coffeeplzbackend.dto.OrderDataCollectionDto;
import com.soonhankwon.coffeeplzbackend.dto.OrderItemDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.event.OrderEvent;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import com.soonhankwon.coffeeplzbackend.utils.Calculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final Calculator calculator;
    private final RedissonLockGenerator redissonLockService;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAllOrders() {
        return orderRepository.findAll().stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

    public OrderResponseDto placeOrder(Long userId, List<OrderRequestDto> orderRequestDto) {
        return redissonLockService.executeOrderWithLock(userId, orderRequestDto);
    }

    @Transactional
    public OrderResponseDto orderProcessing(Long userId, List<OrderRequestDto> orderRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RequestException(ErrorCode.USER_NOT_FOUND));
        checkForPreviousOrder(userId);

        List<Long> itemIds = orderRequestDto.stream()
                .map(OrderRequestDto::getItemId)
                .collect(Collectors.toList());

        List<OrderItemDto> orderItemList = orderRequestDto.stream()
                .map(dto -> {
                    Item item = getItemExistsOrThrowException(dto.getItemId());
                    Long price = calculator.calculatePriceSizeAdditionalFee(dto);
                    return new OrderItemDto(item, price, dto.getItemSize(), dto.getQuantity());
                })
                .collect(Collectors.toList());

        long totalPrice = calculator.calculateTotalPrice(orderItemList);
        Order order = Order.createOrder(user, orderRequestDto, totalPrice, orderItemList);
        orderRepository.save(order);
        applicationEventPublisher.publishEvent(new OrderEvent(this, new OrderDataCollectionDto(userId, itemIds, totalPrice)));
        return new OrderResponseDto(order);
    }

    private void checkForPreviousOrder(Long userId) {
        if (orderRepository.existsByUserIdAndStatus(userId, OrderStatus.ORDERED))
            throw new RequestException(ErrorCode.PREVIOUS_ORDER_EXISTS);
    }

    private Item getItemExistsOrThrowException(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new RequestException(ErrorCode.ITEM_NOT_FOUND));
    }
}

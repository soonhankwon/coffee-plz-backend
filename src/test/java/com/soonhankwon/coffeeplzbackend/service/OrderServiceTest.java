package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.domain.*;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.OrderItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    static
    UserRepository userRepository;
    @Mock
    OrderItemRepository orderItemRepository;

    @InjectMocks
    OrderService orderService;

    @BeforeAll
    static void beforeAll() {

    }

    @Test
    void findAllOrders() {
        //given
        List<Order> order =
                Arrays.asList(Order.builder().orderId(1L).orderType(OrderType.TAKEOUT).totalPrice(12000L).build(),
                        Order.builder().orderId(2L).orderType(OrderType.TAKEOUT).totalPrice(9000L).build());

        when(orderRepository.findAll()).thenReturn(order);

        //when
        List<OrderResponseDto> result = orderService.findAllOrders();

        //then
        verify(orderRepository, times(1)).findAll();
        assertThat(result.get(0).getOrderId(), equalTo(1L));
        assertThat(result.get(1).getType(), equalTo(OrderType.TAKEOUT));
    }

    @Test
    @DisplayName("주문 프로세스 테스트")
    void testOrderProcessing_whenUserHasEnoughPoints() {
        // Given
        Long userId = 1L;
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        User user = new User(1L, loginId, password, email);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        OrderType orderType = OrderType.TAKEOUT;
        List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .itemId(1L)
                .orderItemPrice(2000L)
                .quantity(2).build();
        orderRequestDtoList.add(orderRequestDto);

        Item item = Item.builder()
                .id(1L)
                .name("Americano")
                .price(2000)
                .build();
        when(itemRepository.findById(1L)).thenReturn(Optional.of((item)));

        long totalPrice = 0;
        totalPrice += orderRequestDto.getOrderItemPrice() * orderRequestDto.getQuantity();

        Order order = Order.builder().orderId(1L).orderType(orderType)
                .totalPrice(totalPrice).status(OrderStatus.ORDERED).user(user).build();

        // When
        OrderResponseDto orderResponseDto = orderService.orderProcessing(userId, orderRequestDtoList);

        // Then
        verify(userRepository, times(1)).findById(userId);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
        assertNotNull(orderResponseDto);
        assertEquals(4000L, order.getTotalPrice());
    }
}
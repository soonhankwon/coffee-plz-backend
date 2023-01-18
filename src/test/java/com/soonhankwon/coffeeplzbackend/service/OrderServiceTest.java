package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.request.OrderItemRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Item;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.entity.OrderItem;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.ItemRepository;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    void findAllOrder() {
        //given
        List<Order> order =
                Arrays.asList(Order.builder().id(1L).orderType(Order.OrderType.TAKEOUT).totalPrice(12000L).build(),
                        Order.builder().id(2L).orderType(Order.OrderType.TAKEOUT).totalPrice(9000L).build());

        when(orderRepository.findAll()).thenReturn(order);

        //when
        List<OrderResponseDto> result = orderService.findAllOrder();

        //then
        verify(orderRepository, times(1)).findAll();
        assertThat(result.get(0).getOrderId(), equalTo(1L));
        assertThat(result.get(1).getType(), equalTo(Order.OrderType.TAKEOUT));
    }

    @Test
    @DisplayName("주문 프로세스 테스트")
    public void testOrderProcessing() {
        //given
        Long userId = 1L;
        Long itemId = 2L;
        List<OrderRequestDto> orderRequestDto = new LinkedList<>();
        orderRequestDto.add(new OrderRequestDto(itemId, 1, Order.OrderType.TAKEOUT));

        User user = User.builder().id(userId).loginId("soonable").point(10000L).password("1234").build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Item item = Item.builder().id(2L).name("Americano").price(2000L).size("M").build();
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        Order order = Order.builder().id(1L).totalPrice(2000L).orderType(Order.OrderType.TAKEOUT).status("주문완료").user(user).build();
        when(orderRepository.save(any())).thenReturn(order);

        //when
        OrderResponseDto response = orderService.orderProcessing(userId, orderRequestDto);

        //then
        assertEquals(2000L, response.getTotalPrice());
        assertEquals(Order.OrderType.TAKEOUT, response.getType());
        assertEquals("주문완료", response.getStatus());
    }
}
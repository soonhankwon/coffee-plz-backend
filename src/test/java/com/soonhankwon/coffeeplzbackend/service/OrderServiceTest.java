package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    void findAllOrder() {
        //given
        List<Order> order =
                Arrays.asList(Order.builder().id(1L).type("매장수령").totalPrice(12000L).build(),
                        Order.builder().id(2L).type("배달").totalPrice(9000L).build());

        when(orderRepository.findAll()).thenReturn(order);

        //when
        List<OrderResponseDto> result = orderService.findAllOrder();

        //then
        verify(orderRepository, times(1)).findAll();
        assertThat(result.get(0).getOrderId(), equalTo(1L));
        assertThat(result.get(1).getType(), equalTo("배달"));
    }
}
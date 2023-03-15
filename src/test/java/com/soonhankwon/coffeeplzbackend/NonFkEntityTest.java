package com.soonhankwon.coffeeplzbackend;

import com.soonhankwon.coffeeplzbackend.domain.Order;
import com.soonhankwon.coffeeplzbackend.domain.OrderType;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NonFkEntityTest {
    @Mock
    OrderRepository orderRepository;

    @Test
    @DisplayName("외래키없이 주문 생성")
    void createWithoutUser() {
        Order order = Order.builder().orderId(1L)
                .totalPrice(20000L)
                .orderType(OrderType.STORE)
                .version(0L).build();
        orderRepository.save(order);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order orderSample = orderRepository.findById(1L).orElseThrow();

        assertThat(orderSample.getOrderId()).isEqualTo(1L);
        assertThat(orderSample.getOrderType()).isEqualTo(OrderType.STORE);
    }
}

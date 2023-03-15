package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.domain.*;
import com.soonhankwon.coffeeplzbackend.dto.response.PaymentResponseDto;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
import com.soonhankwon.coffeeplzbackend.repository.PointHistoryRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    PointHistoryRepository pointHistoryRepository;

    @InjectMocks
    PaymentService paymentService;

    @Test
    void paymentProcessing() {
        //given
        String loginId = "test";
        String password = "1234";
        String email = "test@gmail.com";
        User user = new User(1L, loginId, password, email, 15000L);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        Order order = Order.builder().orderId(1L)
                .orderType(OrderType.TAKEOUT)
                .status(OrderStatus.ORDERED)
                .totalPrice(10000L).build();
        when(orderRepository.findByUserIdAndStatus(1L, OrderStatus.ORDERED)).thenReturn(Optional.of(order));
        PointHistory pointHistory = PointHistory.createPointHistory(user, PointHistory.PointType.USAGE, order.getTotalPrice());
        //when
        PaymentResponseDto result = paymentService.paymentProcessing(1L);

        //then
        assertThat(order.getOrderType(), equalTo(OrderType.TAKEOUT));
        assertThat(order.getStatus(), equalTo(OrderStatus.PAID));
        assertThat(result.getMessage(), equalTo("결제완료"));
        assertThat(pointHistory.getPoint(), equalTo(10000L));
    }

    @Test
    void payment_throwsExceptionWhenOrderIsNotOrdered() {
        // Arrange
        Long userId = 1L;
        Order order = Order.builder()
                .orderId(1L)
                .orderType(OrderType.TAKEOUT)
                .totalPrice(10000L)
                .status(OrderStatus.PAID).build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> paymentService.paymentProcessing(userId));
    }

    @Test
    void payment_throwsExceptionWhenOrderDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(orderRepository.findByUserIdAndStatus(userId, OrderStatus.PAID)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NullPointerException.class, () -> paymentService.paymentProcessing(1L));
    }
}
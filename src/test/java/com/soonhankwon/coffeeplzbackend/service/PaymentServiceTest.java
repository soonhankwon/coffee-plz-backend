package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.PaymentResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.OrderRepository;
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

    @InjectMocks
    PaymentService paymentService;

    @Test
    void paymentProcessing() {
        //given
        User user = User.builder().id(1L)
                .loginId("soonhan")
                .email("soonable@gmail.com")
                .password("1234")
                .point(20000L).build();
        userRepository.save(user);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        Order order = Order.builder().orderId(1L)
                .orderType(Order.OrderType.TAKEOUT)
                .totalPrice(10000L).build();
        orderRepository.save(order);
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        //when
        PaymentResponseDto result = paymentService.paymentProcessing(1L,1L);

        //then
        assertThat(user.getPoint(), equalTo(10000L));
        assertThat(order.getOrderType(), equalTo(Order.OrderType.TAKEOUT));
        assertThat(order.getStatus(), equalTo("결제완료"));
        assertThat(result.getMessage(), equalTo("결제완료"));
    }
    @Test
    void payment_throwsExceptionWhenOrderIsNotReady() {
        // Arrange
        Long userId = 1L;
        Order order = Order.builder()
                .orderId(1L)
                .orderType(Order.OrderType.TAKEOUT)
                .totalPrice(10000L)
                .status("결제완료").build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> paymentService.paymentProcessing(1L,userId));
    }

    @Test
    public void payment_throwsExceptionWhenOrderDoesNotExist() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NullPointerException.class, () -> paymentService.paymentProcessing(1L,1L));
    }
}
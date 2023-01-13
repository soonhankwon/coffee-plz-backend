package com.soonhankwon.coffeeplzbackend.service;

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
import static org.junit.jupiter.api.Assertions.*;
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

        Order order = Order.builder().id(1L)
                .type("TakeOut")
                .totalPrice(10000L).build();
        orderRepository.save(order);
        when(orderRepository.findById(any())).thenReturn(Optional.of(order));

        //when
        String result = paymentService.paymentProcessing(1L,1L);

        //then
        assertThat(user.getPoint(), equalTo(10000L));
        assertThat(order.getType(), equalTo("TakeOut"));
        assertThat(result, equalTo("Success"));
    }
}
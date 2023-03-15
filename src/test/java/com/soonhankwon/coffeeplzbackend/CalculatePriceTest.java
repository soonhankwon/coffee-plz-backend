package com.soonhankwon.coffeeplzbackend;

import com.soonhankwon.coffeeplzbackend.domain.ItemSize;
import com.soonhankwon.coffeeplzbackend.domain.OrderItem;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatePriceTest {
    @Test
    void calculatePriceTest() {
        OrderRequestDto orderRequestDto1 = new OrderRequestDto(ItemSize.S, 3000L);
        OrderRequestDto orderRequestDto2 = new OrderRequestDto(ItemSize.M, 3000L);
        OrderRequestDto orderRequestDto3 = new OrderRequestDto(ItemSize.L, 3000L);

        Long ret1 = OrderItem.calculatePrice(orderRequestDto1);
        Long ret2 = OrderItem.calculatePrice(orderRequestDto2);
        Long ret3 = OrderItem.calculatePrice(orderRequestDto3);

        assertThat(ret1).isEqualTo(3000);
        assertThat(ret2).isEqualTo(3500);
        assertThat(ret3).isEqualTo(4000);
    }
}

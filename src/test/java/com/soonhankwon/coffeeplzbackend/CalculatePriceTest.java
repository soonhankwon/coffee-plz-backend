package com.soonhankwon.coffeeplzbackend;

import com.soonhankwon.coffeeplzbackend.domain.ItemSize;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.utils.Calculator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatePriceTest {
    @Test
    void calculatePriceTest() {
        Calculator calculator = new Calculator();
        OrderRequestDto orderRequestDto1 = new OrderRequestDto(ItemSize.S, 3000L);
        OrderRequestDto orderRequestDto2 = new OrderRequestDto(ItemSize.M, 3000L);
        OrderRequestDto orderRequestDto3 = new OrderRequestDto(ItemSize.L, 3000L);

        Long ret1 = calculator.calculatePriceSizeAdditionalFee(orderRequestDto1);
        Long ret2 = calculator.calculatePriceSizeAdditionalFee(orderRequestDto2);
        Long ret3 = calculator.calculatePriceSizeAdditionalFee(orderRequestDto3);

        assertThat(ret1).isEqualTo(3000);
        assertThat(ret2).isEqualTo(3500);
        assertThat(ret3).isEqualTo(4000);
    }
}

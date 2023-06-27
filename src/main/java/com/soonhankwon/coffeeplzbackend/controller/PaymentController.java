package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResDto;
import com.soonhankwon.coffeeplzbackend.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "결제 API")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PatchMapping("/users/orderPayment")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "주문 결제")
    public GlobalResDto orderPayment(Long userId) {
        return paymentService.paymentProcessing(userId);
    }
}

package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.response.PaymentResponseDto;
import com.soonhankwon.coffeeplzbackend.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "결제 API")
@RestController
public class PaymentController {
    private final PaymentService paymentService;
    @PatchMapping("/users/orderPayment")
    @Operation(summary = "주문 결제")
    public ResponseEntity<PaymentResponseDto> orderPayment(Long userId) {
        return ResponseEntity.status(200).body(paymentService.paymentProcessing(userId));
    }
}

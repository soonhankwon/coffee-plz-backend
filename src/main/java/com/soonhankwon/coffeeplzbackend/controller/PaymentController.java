package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.response.PaymentResponseDto;
import com.soonhankwon.coffeeplzbackend.service.PaymentService;
import com.soonhankwon.coffeeplzbackend.service.PointService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Api(tags = "결제 API")
@RestController
public class PaymentController {
    private final PaymentService paymentService;
    @PatchMapping("/user/orderPayment")
    public ResponseEntity<PaymentResponseDto> orderPayment(Long userId, Long orderId) {
        return ResponseEntity.status(200).body(paymentService.paymentProcessing(userId, orderId));
    }
}

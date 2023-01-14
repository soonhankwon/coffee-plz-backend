package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.service.OrderService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@Api(tags = "주문 API")
public class OrderController {
    private final OrderService orderService;
    @PutMapping("/order")
    public ResponseEntity<OrderResponseDto> orderProcessing(OrderRequestDto orderRequestDto) {
        return ResponseEntity.status(200).body(orderService.orderProcessing(orderRequestDto));
    }
    @GetMapping("/order/find")
    public ResponseEntity<List<OrderResponseDto>> findAllOrder() {
        return ResponseEntity.status(200).body(orderService.findAllOrder());
    }
}

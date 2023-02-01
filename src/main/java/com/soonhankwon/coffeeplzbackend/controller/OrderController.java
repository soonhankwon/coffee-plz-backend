package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "주문 API")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/{id}")
    public ResponseEntity<OrderResponseDto> orderProcessing(@PathVariable Long id, @RequestBody List<OrderRequestDto> orderRequestDto) {
        return ResponseEntity.status(200).body(orderService.orderProcessing(id, orderRequestDto));
    }

    @GetMapping("/order/find")
    public ResponseEntity<List<OrderResponseDto>> findAllOrders() {
        return ResponseEntity.status(200).body(orderService.findAllOrders());
    }
}

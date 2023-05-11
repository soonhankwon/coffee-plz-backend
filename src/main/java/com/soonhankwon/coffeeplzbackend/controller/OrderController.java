package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.OrderDto;
import com.soonhankwon.coffeeplzbackend.dto.request.OrderRequestDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderResponseDto;
import com.soonhankwon.coffeeplzbackend.dto.response.OrderSheetResDto;
import com.soonhankwon.coffeeplzbackend.service.OrderServiceSystem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "주문 API")
public class OrderController {
    private final OrderServiceSystem orderServiceSystemImpl;

    @CrossOrigin(origins = "*")
    @PostMapping("/orders/{id}")
    @Operation(summary = "주문 생성")
    public ResponseEntity<OrderResponseDto> orderProcessing(@PathVariable Long id, @RequestBody List<OrderRequestDto> orderRequestDto) {
        return ResponseEntity.status(200).body(orderServiceSystemImpl.placeOrder(new OrderDto(id, orderRequestDto)));
    }

    @GetMapping("/orders/")
    @Operation(summary = "전체 주문 조회")
    public ResponseEntity<List<OrderResponseDto>> findAllOrders() {
        return ResponseEntity.status(200).body(orderServiceSystemImpl.findAllOrders());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/orders/{id}")
    @Operation(summary = "유저 결제시 주문서 조회")
    public ResponseEntity<OrderSheetResDto> findAllOrderItems(@PathVariable Long id) {
        return ResponseEntity.status(200).body(orderServiceSystemImpl.findOrderSheet(id));
    }
}

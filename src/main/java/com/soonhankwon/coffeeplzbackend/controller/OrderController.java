package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.entity.Menu;
import com.soonhankwon.coffeeplzbackend.entity.Order;
import com.soonhankwon.coffeeplzbackend.service.OrderService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@Api(tags = "주문 조회 API")
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/order/")
    public ResponseEntity<List<Order>> findAllOrder() {
        return ResponseEntity.ok(orderService.findAllOrder());
    }
}

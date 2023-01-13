package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.entity.PointHistory;
import com.soonhankwon.coffeeplzbackend.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PointController {
    private final PointService pointService;

    @PatchMapping("/user/point")
    public ResponseEntity<String> chargePoint(String loginId, Long chargePoint) {
        return ResponseEntity.ok(pointService.chargePoint(loginId, chargePoint));
    }
}

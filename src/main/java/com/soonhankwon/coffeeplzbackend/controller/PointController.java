package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.entity.PointHistory;
import com.soonhankwon.coffeeplzbackend.service.PointService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Api(tags = "유저 포인트 충전 API")
public class PointController {
    private final PointService pointService;

    @PatchMapping("/user/point")
    public ResponseEntity<String> chargePoint(String loginId, Long chargePoint) {
        return ResponseEntity.ok(pointService.chargePoint(loginId, chargePoint));
    }
}

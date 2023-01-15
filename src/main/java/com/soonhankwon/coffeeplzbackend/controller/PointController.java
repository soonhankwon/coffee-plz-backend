package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.response.PointResponseDto;
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
    public ResponseEntity<PointResponseDto> chargePoint(String loginId, Long chargePoint) {
        return ResponseEntity.status(200).body(pointService.chargePoint(loginId, chargePoint));
    }
}

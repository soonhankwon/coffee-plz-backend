package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.response.PointResponseDto;
import com.soonhankwon.coffeeplzbackend.service.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "유저 포인트 충전 API")
public class PointController {
    private final PointService pointService;

    @PatchMapping("/user/point/{id}")
    public ResponseEntity<PointResponseDto> chargePoint(@PathVariable Long id, Long chargePoint) {
        return ResponseEntity.status(200).body(pointService.chargePoint(id, chargePoint));
    }
}

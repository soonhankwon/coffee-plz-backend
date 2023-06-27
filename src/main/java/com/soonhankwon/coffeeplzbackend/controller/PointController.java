package com.soonhankwon.coffeeplzbackend.controller;

import com.soonhankwon.coffeeplzbackend.dto.response.GlobalResDto;
import com.soonhankwon.coffeeplzbackend.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "유저 포인트 충전 API")
public class PointController {
    private final PointService pointService;

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/users/point/{id}")
    @Operation(summary = "포인트 충전")
    public GlobalResDto chargePoint(@PathVariable Long id, Long chargePoint) {
        return pointService.chargePoint(id, chargePoint);
    }
}

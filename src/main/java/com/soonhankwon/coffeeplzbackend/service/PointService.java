package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.PointResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class PointService {
    private final UserRepository userRepository;

    @Transactional
    public PointResponseDto chargePoint(String loginId, Long chargePoint) {
        User user = userRepository.findByLoginId(loginId);
        user.setUserPoint(user.getPoint() + chargePoint);
        return new PointResponseDto("포인트 충전 완료");
    }
}

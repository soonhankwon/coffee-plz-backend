package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.PointResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.PointHistory;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.PointRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PointService {
    private final UserRepository userRepository;

    public PointResponseDto chargePoint(String loginId, Long chargePoint) {
        User user = userRepository.findByLoginId(loginId);
        user.setUserPoint(user.getPoint() + chargePoint);
        userRepository.save(user);
        return new PointResponseDto("포인트 충전 완료");
    }
}

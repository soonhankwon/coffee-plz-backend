package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.PointResponseDto;
import com.soonhankwon.coffeeplzbackend.entity.PointHistory;
import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.PointHistoryRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class PointService {
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public PointResponseDto chargePoint(Long id, Long chargePoint) {
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        createPointHistory(user, PointHistory.PointType.CHARGE, chargePoint);
        user.setUserPoint(user.getPoint() + chargePoint);
        return new PointResponseDto("포인트 충전 완료");
    }
    public void createPointHistory(User user, PointHistory.PointType type, Long point) {
        PointHistory pointHistory = new PointHistory(user, type, point);
        pointHistoryRepository.save(pointHistory);
    }
}

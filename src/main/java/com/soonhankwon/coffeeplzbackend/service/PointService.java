package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.dto.response.PointResponseDto;
import com.soonhankwon.coffeeplzbackend.domain.PointHistory;
import com.soonhankwon.coffeeplzbackend.domain.User;
import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.repository.PointHistoryRepository;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.soonhankwon.coffeeplzbackend.domain.PointHistory.createPointHistory;

@AllArgsConstructor
@Service
public class PointService {
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public PointResponseDto chargePoint(Long id, Long chargePoint) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.USER_NOT_FOUND));
        pointHistoryRepository.save(createPointHistory(user, PointHistory.PointType.CHARGE, chargePoint));
        user.setUserPointWithValidChargePoint(chargePoint);
        return new PointResponseDto("포인트 충전 완료");
    }
}

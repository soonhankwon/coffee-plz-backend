package com.soonhankwon.coffeeplzbackend.service;

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
    private final PointRepository pointRepository;

    public String chargePoint(String loginId, Long chargePoint) {
        User user = userRepository.findByLoginId(loginId);
        user.chargeUserPoint(user.getPoint() + chargePoint);
        userRepository.save(user);
        return "Success";
    }
}

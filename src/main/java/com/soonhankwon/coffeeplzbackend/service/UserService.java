package com.soonhankwon.coffeeplzbackend.service;

import com.soonhankwon.coffeeplzbackend.entity.User;
import com.soonhankwon.coffeeplzbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (user.getLoginId().isEmpty()) {
            return null;
        }
        return userRepository.save(user);
    }

    public User findUser(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}

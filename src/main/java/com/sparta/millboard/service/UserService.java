package com.sparta.millboard.service;

import com.sparta.millboard.model.User;
import com.sparta.millboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }
}

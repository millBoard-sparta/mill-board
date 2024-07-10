package com.sparta.millboard.service;

import com.sparta.millboard.dto.response.SignupResponseDto;
import com.sparta.millboard.model.User;
import com.sparta.millboard.dto.request.SignupRequestDto;
import com.sparta.millboard.model.User;
import com.sparta.millboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "UserService")
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }

    // 사용자 : 회원가입
    public SignupResponseDto createUser(SignupRequestDto requestDto) {
        log.info("createUser 메서드 실행");

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();

        userRepository.save(user);

        return new SignupResponseDto(user.getUsername());
    }

}

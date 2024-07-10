package com.sparta.millboard.service;

import com.sparta.millboard.dto.request.UserRequestDto;
import com.sparta.millboard.dto.response.UserResponseDto;
import com.sparta.millboard.model.User;
import com.sparta.millboard.repository.UserRepository;
import com.sparta.millboard.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.sparta.millboard.model.User.Role.USER;

@Service
@Slf4j(topic = "UserService")
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }

    // 사용자 : 회원가입
    public UserResponseDto createUser(UserRequestDto requestDto) {
        log.info("createUser 메서드 실행");

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(USER)
                .build();

        userRepository.save(user);

        return new UserResponseDto(user.getUsername());
    }

    public UserResponseDto loginUser(UserRequestDto requestDto, HttpServletResponse response) {
        log.info("loginUser 메서드 실행");

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(requestDto.getPassword(),user.getPassword())) {
            throw new RuntimeException("비밀번호 오류");
        }

        String accessToken = jwtService.generateAccessToken(requestDto.getUsername());
        String refreshToken = jwtService.generateRefreshToken(requestDto.getUsername());

        user.addRefreshToken(refreshToken);
        userRepository.save(user);

        response.setHeader("Authorization", accessToken);

        return new UserResponseDto(user.getUsername(),accessToken);
    }
}

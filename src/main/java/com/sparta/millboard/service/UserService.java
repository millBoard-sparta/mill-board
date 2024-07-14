package com.sparta.millboard.service;

import com.sparta.millboard.dto.request.UserRequestDto;
import com.sparta.millboard.dto.request.UserUpdateRequestDto;
import com.sparta.millboard.dto.response.LoginResponseDto;
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

        return new UserResponseDto(user);
    }

    // 사용자 : 로그인
    public LoginResponseDto loginUser(UserRequestDto requestDto, HttpServletResponse response) {
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

        return new LoginResponseDto(user.getUsername(),accessToken,refreshToken);
    }

    // 사용자 : 프로필 조회
    public UserResponseDto getUser(User user) {

        log.info("updateUser 메서드 실행 User : " + user);

        User profile = userRepository.findByUsername(user.getUsername()).orElseThrow(()
                -> new RuntimeException("사용자를 찾을 수 없습니다. username : " +user.getUsername()));

        log.info("updateUser 메서드 종료 profile : " + profile);
        return new UserResponseDto(profile);
    }

    // 사용자 : 프로필 수정
    public UserResponseDto updateUser(UserUpdateRequestDto requestDto, User user) {
        log.info("updateUser 메서드 실행");

        User newProfile = userRepository.findByUsername(user.getUsername()).orElseThrow(() ->
                new RuntimeException("username 에 맞는 user 를 찾을 수 없음"));

        if (requestDto.getNewUsername() != null) {
            String newUsername = requestDto.getNewUsername().trim();
            if (!newUsername.isEmpty()) {
                user.updateUsername(newUsername);
            } else {
                throw new IllegalArgumentException("새 사용자 이름은 공백일 수 없습니다.");
            }
        }

        if (requestDto.getPassword() != null && !requestDto.getPassword().trim().isEmpty()) {
            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
            }

            if (requestDto.getNewPassword() != null) {
                String newPassword = requestDto.getNewPassword().trim();
                if (!newPassword.isEmpty()) {
                    if (passwordEncoder.matches(newPassword, user.getPassword())) {
                        throw new IllegalArgumentException("새 비밀번호는 이전 비밀번호와 동일할 수 없습니다.");
                    }
                    user.updatePassword(passwordEncoder.encode(newPassword));
                } else {
                    throw new IllegalArgumentException("새 비밀번호는 공백일 수 없습니다.");
                }
            }
        }

        userRepository.save(newProfile);

        return new UserResponseDto(newProfile);
    }

    // 사용자 : 토큰 재발급
    public LoginResponseDto refreshAccessToken(User user) {
        String refreshToken = user.getRefreshToken();
        if (refreshToken == null || !jwtService.isValidToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateAccessToken(user.getUsername());

        return new LoginResponseDto(user.getUsername(), newAccessToken, refreshToken);
    }

}

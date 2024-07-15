package com.sparta.millboard.controller;

import com.sparta.millboard.common.CommonResponse;
import com.sparta.millboard.dto.request.UserRequestDto;
import com.sparta.millboard.dto.request.UserUpdateRequestDto;
import com.sparta.millboard.dto.response.LoginResponseDto;
import com.sparta.millboard.dto.response.UserResponseDto;
import com.sparta.millboard.security.UserPrincipal;
import com.sparta.millboard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "UserController")
@Tag(name = "A. 사용자", description = "사용자")
public class UserController {

    private final UserService userService;

    // 사용자 : 회원가입
    @PostMapping("/api/users/signup")
    @Operation(summary = "회원가입", description = "사용자 : 회원가입")
    public ResponseEntity<CommonResponse<UserResponseDto>> createUser(
            @RequestBody UserRequestDto requestDto
    ) {

        log.info("createUser 메서드 실행");
        UserResponseDto responseDto = userService.createUser(requestDto);

        CommonResponse<UserResponseDto> response = new CommonResponse<>(
                "회원가입 성공",
                HttpStatus.CREATED.value(),
                responseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    // 사용자 : 로그인
    @PostMapping("/api/users/login")
    @Operation(summary = "로그인", description = "사용자 : 로그인")
    public ResponseEntity<CommonResponse<LoginResponseDto>> loginUser(
            @RequestBody UserRequestDto requestDto,
            HttpServletResponse httpServletResponse
    ) {

        log.info("loginUser 메서드 실행");
        LoginResponseDto responseDto = userService.loginUser(requestDto,httpServletResponse);

        CommonResponse<LoginResponseDto> response = new CommonResponse<>(
                "로그인 성공",
                HttpStatus.OK.value(),
                responseDto
        );

        httpServletResponse.setHeader("Location", "/main");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // 사용자 : 프로필 조회
    @GetMapping("/api/users/profile")
    @Operation(summary = "프로필 조회", description = "사용자 : 프로필 조회")
    public ResponseEntity<CommonResponse<UserResponseDto>> readUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ) {

      log.info("readUser 메서드 실행. 받아온 userPrincipal : " + userPrincipal);
      log.info("readUser 메서드 실행. 받아온 userPrincipal.getUsername : " + userPrincipal.getUsername());
        UserResponseDto responseDto = userService.getUser(userPrincipal.getUser());

        CommonResponse<UserResponseDto> response = new CommonResponse<>(
                "프로필 조회 성공",
                HttpStatus.OK.value(),
                responseDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 사용자 : 회원 정보 수정
    @PutMapping("/api/users/profile")
    @Operation(summary = "프로필 수정", description = "사용자 : 프로필 수정")
    public ResponseEntity<CommonResponse<UserResponseDto>> updateUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody UserUpdateRequestDto requestDto
    ) {

        log.info("updateUser 메서드 실행");
        UserResponseDto responseDto = userService.updateUser(requestDto,userPrincipal.getUser());

        CommonResponse<UserResponseDto> response = new CommonResponse<>(
                "프로필 수정 완료",
                HttpStatus.OK.value(),
                responseDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // 사용자 : 토큰 재발급
    @PutMapping("/api/token/refresh")
    @Operation(summary = "토큰 재발급", description = "사용자 : 토큰 재발급")
    public ResponseEntity<CommonResponse<LoginResponseDto>> reRefreshToken(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {

        LoginResponseDto responseDto = userService.refreshAccessToken(userPrincipal.getUser());

        CommonResponse<LoginResponseDto> response = new CommonResponse<>(
                "액세스 토큰 재발급 성공",
                HttpStatus.OK.value(),
                responseDto
        );

        return ResponseEntity.ok(response);
    }
}

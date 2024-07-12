package com.sparta.millboard.controller;

import com.sparta.millboard.common.CommonResponse;
import com.sparta.millboard.dto.request.UserRequestDto;
import com.sparta.millboard.dto.request.UserUpdateRequestDto;
import com.sparta.millboard.dto.response.LoginResponseDto;
import com.sparta.millboard.dto.response.UserResponseDto;
import com.sparta.millboard.security.UserPrincipal;
import com.sparta.millboard.service.UserService;
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
public class UserController {

    private final UserService userService;

    // 사용자 : 회원가입
    @PostMapping("/api/users/signup")
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

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // 사용자 : 프로필 조회
    @GetMapping("/api/users/profile")
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

}

package com.sparta.millboard.controller;

import com.sparta.millboard.common.CommonResponse;
import com.sparta.millboard.dto.request.UserRequestDto;
import com.sparta.millboard.dto.response.UserResponseDto;
import com.sparta.millboard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users/signup")
    public ResponseEntity<CommonResponse<UserResponseDto>> createUser(
            @RequestBody UserRequestDto requestDto
    ) {

        UserResponseDto responseDto = userService.createUser(requestDto);

        CommonResponse<UserResponseDto> response = new CommonResponse<>(
                "회원가입 성공",
                HttpStatus.CREATED.value(),
                responseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/api/users/login")
    public ResponseEntity<CommonResponse<UserResponseDto>> loginUser(
            @RequestBody UserRequestDto requestDto,
            HttpServletResponse httpServletResponse
    ) {

        UserResponseDto responseDto = userService.loginUser(requestDto,httpServletResponse);

        CommonResponse<UserResponseDto> response = new CommonResponse<>(
                "로그인 성공",
                HttpStatus.OK.value(),
                responseDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}

package com.sparta.millboard.controller;

import com.sparta.millboard.common.CommonResponse;
import com.sparta.millboard.dto.request.SignupRequestDto;
import com.sparta.millboard.dto.response.SignupResponseDto;
import com.sparta.millboard.model.User;
import com.sparta.millboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/users/signup")
    public ResponseEntity<CommonResponse<SignupResponseDto>> createUser(@RequestBody SignupRequestDto requestDto) {

        SignupResponseDto signupResponseDto = userService.createUser(requestDto);

        CommonResponse<SignupResponseDto> response = new CommonResponse<>(
                "회원가입 성공",
                HttpStatus.CREATED.value(),
                signupResponseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

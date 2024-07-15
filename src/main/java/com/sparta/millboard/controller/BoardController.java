package com.sparta.millboard.controller;

import com.sparta.millboard.common.CommonResponse;
import com.sparta.millboard.dto.request.AddPartnerDto;
import com.sparta.millboard.dto.request.BoardCreateDto;
import com.sparta.millboard.dto.request.BoardUpdateDto;
import com.sparta.millboard.dto.response.BoardResponseDto;
import com.sparta.millboard.model.User;
import com.sparta.millboard.service.BoardService;
import com.sparta.millboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/api/boards")
    public ResponseEntity<?> createBoard(@RequestBody BoardCreateDto boardCreateDto) {

        CommonResponse<BoardResponseDto> response = new CommonResponse<BoardResponseDto>(
                "보드생성성공",
                HttpStatus.CREATED.value(),
                boardService.createBoard(boardCreateDto)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/boards/{boardId}")
    public ResponseEntity<?> showBoard(@PathVariable("boardId") Long id) {
        return ResponseEntity.ok(new CommonResponse(
                "보드 보기",
                HttpStatus.OK.value(),
                boardService.getBoardWithColumnsAndCards(id)
        ));
    }

    @PutMapping("/api/boards/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable("boardId") Long id,
                                         @RequestBody BoardUpdateDto boardUpdateDto) {
        return ResponseEntity.ok(
                new CommonResponse(
                        "보드 수정",
                        HttpStatus.OK.value(),
                        boardService.updateBoard(id, boardUpdateDto)
                ));
    }

    @DeleteMapping("/api/boards/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable("boardId") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok(
                new CommonResponse(
                        "보드 삭제",
                        HttpStatus.OK.value(),
                        null
                ));
    }

    @PostMapping("/api/boards/{boardId}/partners")
    public ResponseEntity<?> addPartner(@PathVariable("boardId") Long boardId,
                                        @RequestBody AddPartnerDto requestDto) {
        User user = userService.getById(requestDto.getUserId());
        return ResponseEntity.ok(
                new CommonResponse(
                        "보드 파트너 추가",
                        HttpStatus.OK.value(),
                        boardService.addPartner(boardId, user)
                ));

    }

    @DeleteMapping("/api/boards/{boardId}/partners/{partnerId}")
    public ResponseEntity<?> removePartner(@PathVariable("boardId") Long boardId,
                                           @PathVariable("partnerId") Long partnerId) {
        boardService.deletePartner(boardId, partnerId);
        return ResponseEntity.ok(
                new CommonResponse(
                        "보드 파트너 제거",
                        HttpStatus.NO_CONTENT.value(),
                        null
                ));
    }
}

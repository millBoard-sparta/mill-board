package com.sparta.millboard.controller;

import com.sparta.millboard.dto.request.AddPartnerDto;
import com.sparta.millboard.dto.request.BoardCreateDto;
import com.sparta.millboard.dto.request.BoardUpdateDto;
import com.sparta.millboard.model.User;
import com.sparta.millboard.service.BoardService;
import com.sparta.millboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/api/boards")
    public ResponseEntity<?> createBoard(@RequestBody BoardCreateDto boardCreateDto) {
        return ResponseEntity.ok(boardService.createBoard(boardCreateDto));
    }

    @GetMapping("/api/boards/{boardId}")
    public ResponseEntity<?> showBoard(@PathVariable("boardId") Long id) {
        return ResponseEntity.ok(boardService.getBoardWithColumns(id));
//        return ResponseEntity.ok(boardService.getBoardById(id));
    }

    @PutMapping("/api/boards/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable("boardId") Long id,
                                         @RequestBody BoardUpdateDto boardUpdateDto) {
        return ResponseEntity.ok(boardService.updateBoard(id, boardUpdateDto));
    }

    @DeleteMapping("/api/boards/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable("boardId") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

    /*
     *
     */
    @PostMapping("/api/boards/{boardId}/partners")
    public ResponseEntity<?> addPartner(@PathVariable("boardId") Long boardId,
                                        @RequestBody AddPartnerDto requestDto) {
        User user = userService.getById(requestDto.getUserId());
        return ResponseEntity.ok(boardService.addPartner(boardId, user));
    }

    @DeleteMapping("/api/boards/{boardId}/partners/{partnerId}")
    public ResponseEntity<?> removePartner(@PathVariable("boardId") Long boardId,
                                           @PathVariable("partnerId") Long partnerId) {
        boardService.deletePartner(boardId, partnerId);
        return ResponseEntity.ok("제거 성공");
    }
}

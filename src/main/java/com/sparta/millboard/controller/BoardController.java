package com.sparta.millboard.controller;

import com.sparta.millboard.dto.request.BoardCreateDto;
import com.sparta.millboard.dto.request.BoardUpdateDto;
import com.sparta.millboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/board")
    public ResponseEntity<?> createBoard(@RequestBody BoardCreateDto boardCreateDto) {
        return ResponseEntity.ok(boardService.createBoard(boardCreateDto));
    }

    @GetMapping("/api/board/{boardId}")
    public ResponseEntity<?> showBoard(@PathVariable("boardId") Long id) {
        return ResponseEntity.ok(boardService.getBoardById(id));
    }

    @PutMapping("/api/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable("boardId") Long id,
                                         @RequestBody BoardUpdateDto boardUpdateDto) {
        return ResponseEntity.ok(boardService.updateBoard(id, boardUpdateDto));
    }

    @DeleteMapping("/api/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable("boardId") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}

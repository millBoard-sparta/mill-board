package com.sparta.millboard.controller;

import com.sparta.millboard.dto.request.BoardColumnCreateDto;
import com.sparta.millboard.dto.request.BoardColumnUpdateDto;
import com.sparta.millboard.service.BoardColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardColumnController {
    private final BoardColumnService boardColumnService;

    @PostMapping("/api/boards/{boardId}/columns")
    public ResponseEntity<?> createBoardColumn(@Valid @RequestBody BoardColumnCreateDto requestDto,
                                               @PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardColumnService.createBoardColumn(requestDto, boardId));

    }

    @PutMapping("/api/boards/{boardId}/columns/{columnId}")
    public ResponseEntity<?> updateBoardColumn(@RequestBody BoardColumnUpdateDto requestDto,
                                               @PathVariable("boardId") Long boardId,
                                               @PathVariable("columnId") Long columnId) {
        return ResponseEntity.ok(boardColumnService.updateBoardColumn(requestDto, columnId));
    }

    @DeleteMapping("/api/boards/{boardId}/columns/{columnId}")
    public ResponseEntity<?> deleteBoardColumn(@PathVariable("columnId") Long columnId) {
        boardColumnService.deleteById(columnId);
        return ResponseEntity.noContent().build();
    }
}

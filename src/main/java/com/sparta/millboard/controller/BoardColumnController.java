package com.sparta.millboard.controller;

import com.sparta.millboard.common.CommonResponse;
import com.sparta.millboard.dto.request.BoardColumnCreateDto;
import com.sparta.millboard.dto.request.BoardColumnUpdateDto;
import com.sparta.millboard.service.BoardColumnService;
import com.sparta.millboard.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "C. 컬럼", description = "컬럼")
public class BoardColumnController {
    private final BoardColumnService boardColumnService;
    private final BoardService boardService;

    // board 의 모든 columns
    @GetMapping("/api/boards/{boardId}/columns")
    @Operation(summary = "보드 조회", description = "컬럼 : 보드 조회")
    public ResponseEntity<?> getBoardWithColumns(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(
                new CommonResponse(
                        "보드 조회",
                        HttpStatus.OK.value(),
                        boardService.getBoardWithColumnsAndCards(boardId)
                )
        );
    }

    @PostMapping("/api/boards/{boardId}/columns")
    @Operation(summary = "보드 컬럼 생성", description = "컬럼 : 보드 컬럼 생성")
    public ResponseEntity<?> createBoardColumn(@Valid @RequestBody BoardColumnCreateDto requestDto,
                                               @PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(
                new CommonResponse(
                        "보드 칼럼 생성",
                        HttpStatus.CREATED.value(),
                        boardColumnService.createBoardColumn(requestDto, boardId)
                )
        );
    }

    @PutMapping("/api/boards/{boardId}/columns/{columnId}")
    @Operation(summary = "보드 컬럼 수정", description = "컬럼 : 보드 컬럼 수정")
    public ResponseEntity<?> updateBoardColumn(@RequestBody BoardColumnUpdateDto requestDto,
                                               @PathVariable("boardId") Long boardId,
                                               @PathVariable("columnId") Long columnId) {
        return ResponseEntity.ok(
                new CommonResponse(
                        "보드 칼럼 수정",
                        HttpStatus.OK.value(),
                        boardColumnService.updateBoardColumn(requestDto, columnId)
                )
        );

    }

    @DeleteMapping("/api/boards/{boardId}/columns/{columnId}")
    @Operation(summary = "보드 컬럼 삭제", description = "컬럼 : 보드 컬럼 삭제")
    public ResponseEntity<?> deleteBoardColumn(@PathVariable("columnId") Long columnId) {
        boardColumnService.deleteById(columnId);
        return ResponseEntity.ok(
                new CommonResponse(
                        "보드 칼럼 수정",
                        HttpStatus.OK.value(),
                        null
                )
        );

    }
}

package com.sparta.millboard.controller;

import com.sparta.millboard.common.CommonResponse;
import com.sparta.millboard.dto.request.CommentRequestDto;
import com.sparta.millboard.dto.response.CommentResponseDto;
import com.sparta.millboard.service.CommentService;
import com.sparta.millboard.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/card/{cardId}/comment")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                           @PathVariable Long cardId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        CommentResponseDto comment = commentService.createComment(commentRequestDto, cardId, userPrincipal);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CommonResponse<>(
                        "댓글 생성 성공",
                        HttpStatus.CREATED.value(),
                        comment
                )
        );
    }

    @GetMapping("/api/card/{cardId}/comment")
    public ResponseEntity<?> getCommentsByCardId(@PathVariable Long cardId) {
        List<CommentResponseDto> comments = commentService.getCommentsByCardId(cardId);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        "댓글 전체 조회 성공",
                        HttpStatus.OK.value(),
                        comments
                )
        );
    }

    @GetMapping("/api/card/comment/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable Long commentId) {
        CommentResponseDto comment = commentService.getComment(commentId);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        "댓글 조회 성공",
                        HttpStatus.OK.value(),
                        comment
                )
        );
    }

    @DeleteMapping("/api/card/{cardId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long cardId,
                                           @PathVariable Long commentId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        commentService.deleteComment(commentId, userPrincipal);
        return ResponseEntity.ok(
                new CommonResponse<>(
                        "댓글 삭제 성공",
                        HttpStatus.NO_CONTENT.value(),
                        null
                )
        );
    }
}

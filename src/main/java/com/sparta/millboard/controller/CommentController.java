package com.sparta.millboard.controller;

import com.sparta.millboard.dto.request.CommentRequestDto;
import com.sparta.millboard.dto.response.CommentResponseDto;
import com.sparta.millboard.service.CommentService;
import com.sparta.millboard.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{cardId}/comment")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                                            @PathVariable Long cardId,
                                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        CommentResponseDto comment = commentService.createComment(commentRequestDto, cardId, userPrincipal);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{cardId}/comment")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByCardId(@PathVariable Long cardId) {
        List<CommentResponseDto> comments = commentService.getCommentsByCardId(cardId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long commentId) {
        CommentResponseDto comment = commentService.getComment(commentId);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        commentService.deleteComment(commentId, userPrincipal);
        return ResponseEntity.ok("댓글 삭제 성공!");
    }
}

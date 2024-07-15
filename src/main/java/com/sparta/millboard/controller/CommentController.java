package com.sparta.millboard.controller;

import com.sparta.millboard.dto.request.CommentRequestDto;
import com.sparta.millboard.dto.response.CommentResponseDto;
import com.sparta.millboard.service.CommentService;
import com.sparta.millboard.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
@Tag(name = "E. 댓글", description = "댓글")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{cardId}/comment")
    @Operation(summary = "댓글 생성", description = "댓글 : 댓글 생성")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto,
                                                            @PathVariable Long cardId,
                                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        CommentResponseDto comment = commentService.createComment(commentRequestDto, cardId, userPrincipal);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{cardId}/comment")
    @Operation(summary = "댓글 조회", description = "댓글 : 댓글 조회")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByCardId(@PathVariable Long cardId) {
        List<CommentResponseDto> comments = commentService.getCommentsByCardId(cardId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{commentId}")
    @Operation(summary = "댓글 단건 조회", description = "댓글 : 댓글 단건 조회")
    public ResponseEntity<CommentResponseDto> getComment(@PathVariable Long commentId) {
        CommentResponseDto comment = commentService.getComment(commentId);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/comment/{commentId}")
    @Operation(summary = "댓글 수정", description = "댓글 : 댓글 수정")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        CommentResponseDto updatedComment = commentService.updateComment(commentRequestDto, commentId, userPrincipal);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글 : 댓글 삭제")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        commentService.deleteComment(commentId, userPrincipal);
        return ResponseEntity.ok("제거 성공");
    }
}

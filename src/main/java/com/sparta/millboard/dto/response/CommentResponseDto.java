package com.sparta.millboard.dto.response;

import com.sparta.millboard.model.Comment;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentResponseDto {

    private Long id;
    private String content;
    private Long cardId;
    private Long authorId;

    public static CommentResponseDto from(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .cardId(comment.getCard().getId())
                .authorId(comment.getAuthor().getId())
                .build();
    }
}

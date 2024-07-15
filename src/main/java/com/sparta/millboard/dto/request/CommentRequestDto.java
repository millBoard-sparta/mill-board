package com.sparta.millboard.dto.request;

import com.sparta.millboard.model.Comment;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentRequestDto {

    @NotEmpty
    private String content;

    public Comment toEntity(){
        return Comment.builder()
                .content(content)
                .build();
    }
}

package com.sparta.millboard.dto.response;

import com.sparta.millboard.model.Board;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardResponseDto {
    private Long id;
    private String title;
    private String description;

    public static BoardResponseDto from(Board board) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .description(board.getDescription())
                .build();
    }
}

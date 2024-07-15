package com.sparta.millboard.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.millboard.model.Board;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BoardResponseDto {
    private Long id;
    private String title;
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BoardColumnResponseDto> columns;

    public static BoardResponseDto from(Board board) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .description(board.getDescription())
                .build();
    }

    public static BoardResponseDto from(Board board, boolean withColumns) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .description(board.getDescription())
                .columns(withColumns ? board.getBoardColumns().stream().map(c -> BoardColumnResponseDto.from(c, true)).toList() : null)
                .build();
    }

}

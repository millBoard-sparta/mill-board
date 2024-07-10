package com.sparta.millboard.dto.request;

import com.sparta.millboard.model.Board;
import lombok.Data;

@Data
public class BoardUpdateDto {
    private String title;
    private String description;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .description(description)
                .build();
    }
}

package com.sparta.millboard.dto.request;

import com.sparta.millboard.model.Board;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BoardCreateDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    public Board toEntity() {
        return Board.builder().title(title).description(description).build();
    }
}

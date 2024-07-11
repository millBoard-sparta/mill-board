package com.sparta.millboard.dto.request;

import com.sparta.millboard.model.Board;
import com.sparta.millboard.model.BoardColumn;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardColumnCreateDto {
    private String statusName;
    private Integer order;

    public BoardColumn toEntity() {
        return BoardColumn.builder().statusName(statusName).order(order).build();
    }
}

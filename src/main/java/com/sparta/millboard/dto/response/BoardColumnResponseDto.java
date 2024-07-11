package com.sparta.millboard.dto.response;

import com.sparta.millboard.model.BoardColumn;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardColumnResponseDto {
    private Long id;
    private String statusName;
    private Integer order;

    public static BoardColumnResponseDto from(BoardColumn bc) {
        return BoardColumnResponseDto.builder()
                .id(bc.getId())
                .order(bc.getOrder())
                .statusName(bc.getStatusName())
                .build();
    }
}

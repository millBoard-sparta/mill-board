package com.sparta.millboard.dto.response;

import com.sparta.millboard.model.BoardColumn;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class BoardColumnResponseDto {
    private Long id;
    private String statusName;
    private Integer order;
    private List<CardResponseDto> cards;

    public static BoardColumnResponseDto from(BoardColumn bc) {
        return BoardColumnResponseDto.builder()
                .id(bc.getId())
                .order(bc.getOrder())
                .statusName(bc.getStatusName())
                .build();
    }

    public static BoardColumnResponseDto from(BoardColumn bc, boolean withCards) {
        return BoardColumnResponseDto.builder()
                .id(bc.getId())
                .order(bc.getOrder())
                .statusName(bc.getStatusName())
                .cards(withCards ? bc.getCardList().stream().map(CardResponseDto::from).toList() : null)
                .build();
    }

}

package com.sparta.millboard.dto.response;

import com.sparta.millboard.model.Card;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CardResponseDto {

    private Long id;
    private String title;
    private String description;
    private String dueDate;

    public static CardResponseDto from(Card card){
        return CardResponseDto.builder()
            .id(card.getId())
            .title(card.getTitle())
            .description(card.getDescription())
            .dueDate(card.getDueDate()).build();
    }

}

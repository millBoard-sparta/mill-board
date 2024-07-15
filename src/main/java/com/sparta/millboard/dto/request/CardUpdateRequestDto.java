package com.sparta.millboard.dto.request;

import com.sparta.millboard.model.Card;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CardUpdateRequestDto {


    @NotEmpty
    private String title;


    private String description;

    private String  dueDate;

    private Long workerId;
    private Long columnId;

    public Card toEntity(){
        return Card.builder()
            .title(title)
            .description(description)
            .dueDate(dueDate).build();
    }
}

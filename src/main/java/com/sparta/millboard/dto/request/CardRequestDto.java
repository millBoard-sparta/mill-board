package com.sparta.millboard.dto.request;

import com.sparta.millboard.model.Card;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CardRequestDto {

    @NotEmpty
    private String title;

    private String description;

    private String  dueDate;

    public Card toEntity(){
        return Card.builder()
            .title(title)
            .description(description)
            .dueDate(dueDate).build();
    }

}

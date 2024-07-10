package com.sparta.millboard.dto.response;

import com.sparta.millboard.model.BoardPartner;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardPartnerResponseDto {
    private Long id;
    private Long boardId;
    private Long userId;

    public static BoardPartnerResponseDto from(BoardPartner partner) {
        return BoardPartnerResponseDto.builder()
                .id(partner.getId())
                .boardId(partner.getBoard().getId())
                .userId(partner.getUser().getId())
                .build();
    }

}

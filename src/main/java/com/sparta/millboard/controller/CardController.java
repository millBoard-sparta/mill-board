package com.sparta.millboard.controller;

import com.sparta.millboard.common.CommonResponse;
import com.sparta.millboard.dto.request.CardRequestDto;
import com.sparta.millboard.dto.request.CardUpdateRequestDto;
import com.sparta.millboard.security.UserPrincipal;
import com.sparta.millboard.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "D. 카드", description = "카드")
public class CardController {

    private final CardService cardService;

    @PostMapping("/api/columns/{columnId}/cards")
    @Operation(summary = "카드 생성", description = "카드 : 카드 생성")
    public ResponseEntity<?> createCard(@RequestBody CardRequestDto cardRequestDto,
        @PathVariable Long columnId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(
            new CommonResponse(
                "카드 생성",
                HttpStatus.CREATED.value(),
                cardService.createCard(cardRequestDto, columnId, userPrincipal)
            )
        );
    }

    @GetMapping("/api/cards/{cardId}")
    @Operation(summary = "카드 조회", description = "카드 : 카드 조회")
    public ResponseEntity<?> getCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(
            new CommonResponse(
                "카드 조회",
                HttpStatus.OK.value(),
                cardService.getCard(cardId)
            )
        );
    }

    @GetMapping("/api/cards")
    @Operation(summary = "모든 카드 조회", description = "카드 : 모든 카드 조회")
    public ResponseEntity<?> getCards(
        @RequestParam(required = false, defaultValue = "-1") Long columnId,
        @RequestParam(required = false, defaultValue = "-1") Long userId) {
        return ResponseEntity.ok(
            new CommonResponse(
                "카드 페이지 조회",
                HttpStatus.OK.value(),
                cardService.getCards(columnId, userId)
            )
        );
    }

    @PutMapping("/api/columns/{columnId}/cards/{cardId}")
    @Operation(summary = "카드 수정", description = "카드 : 카드 수정")
    public ResponseEntity<?> updateCard(@RequestBody CardUpdateRequestDto cardUpdateRequestDto,
        @PathVariable Long cardId,
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(
            new CommonResponse(
                "카드 업데이트",
                HttpStatus.OK.value(),
                cardService.updateCard(cardUpdateRequestDto, cardId, userPrincipal)
            )
        );
    }

    @DeleteMapping("/api/columns/{columnId}/cards/{cardId}")
    @Operation(summary = "카드 삭제", description = "카드 : 카드 삭제")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        cardService.deleteCard(cardId, userPrincipal);
        return ResponseEntity.ok(
                new CommonResponse(
                        "카드 삭제",
                        HttpStatus.OK.value(),
                        null
                )
        );
    }
}

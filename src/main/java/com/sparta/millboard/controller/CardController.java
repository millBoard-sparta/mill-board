package com.sparta.millboard.controller;

import com.sparta.millboard.dto.request.CardRequestDto;
import com.sparta.millboard.security.UserPrincipal;
import com.sparta.millboard.service.CardService;
import lombok.RequiredArgsConstructor;
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
public class CardController {

    private final CardService cardService;

    @PostMapping("/api/columns/{columnId}/cards")
    public ResponseEntity<?> createCard(@RequestBody CardRequestDto cardRequestDto,
        @PathVariable Long columnId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(cardService.createCard(cardRequestDto, columnId,userPrincipal));
    }

    @GetMapping("/api/cards/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getCard(cardId));
    }

    @GetMapping("/api/cards")
    public ResponseEntity<?> getCards(
        @RequestParam(required = false, defaultValue = "-1") Long columnId,
        @RequestParam(required = false,defaultValue = "-1") Long userId) {
        return ResponseEntity.ok(cardService.getCards(columnId, userId));
    }

    @PutMapping("/api/columns/{columnId}/cards/{cardId}")
    public ResponseEntity<?> updateCard(@RequestBody CardRequestDto cardRequestDto,
        @PathVariable Long columnId, @PathVariable Long cardId,@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(cardService.updateCard(cardRequestDto, columnId, cardId,userPrincipal));
    }


    @DeleteMapping("/api/columns/{columnId}/cards/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long cardId,@AuthenticationPrincipal UserPrincipal userPrincipal) {
        cardService.deleteCard(cardId,userPrincipal);
        return ResponseEntity.noContent().build();
    }
}

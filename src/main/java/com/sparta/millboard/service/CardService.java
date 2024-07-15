package com.sparta.millboard.service;

import com.sparta.millboard.dto.request.CardRequestDto;
import com.sparta.millboard.dto.request.CardUpdateRequestDto;
import com.sparta.millboard.dto.response.CardResponseDto;
import com.sparta.millboard.model.BoardColumn;
import com.sparta.millboard.model.Card;
import com.sparta.millboard.model.User;
import com.sparta.millboard.repository.CardRepository;
import com.sparta.millboard.security.UserPrincipal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "cardService")
public class CardService {

    private final CardRepository cardRepository;
    private final BoardColumnService boardColumnService;
    private final UserService userService;

    public CardResponseDto createCard(CardRequestDto cardRequestDto, Long columnId,
        UserPrincipal userPrincipal) {

        BoardColumn column = boardColumnService.getById(columnId);
        User user = userPrincipal.getUser();

        Card card = cardRequestDto.toEntity();
        card.setColumn(column);
        card.setAuthor(user.getUsername());

        if(cardRequestDto.getWorkerId()>0){
            card.setWorker(userService.getById(cardRequestDto.getWorkerId()));
        }

        cardRepository.save(card);

        return CardResponseDto.from(card);
    }

    @Transactional
    public CardResponseDto updateCard(CardUpdateRequestDto cardRequestDto, Long columnId, Long cardId,
        UserPrincipal userPrincipal) {

        Card card = getById(cardId);
        String tryUsername = userPrincipal.getUser().getUsername();
        if (!Objects.equals(card.getAuthorName(), tryUsername)) {
            return null;
        }

        if(cardRequestDto.getColumnId()>0){
            BoardColumn column = boardColumnService.getById(cardRequestDto.getColumnId());
            card.updateColumn(column);
        }

        card.update(cardRequestDto.toEntity());

        if(cardRequestDto.getWorkerId()>0){
            card.setWorker(userService.getById(cardRequestDto.getWorkerId()));
        }

        return CardResponseDto.from(card);
    }


    public CardResponseDto getCard(Long cardId) {
        return CardResponseDto.from(getById(cardId));
    }

    public Page<CardResponseDto> getCards(Long columnId, Long userId) {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Card> cardPage;
        if (columnId <= 0 && userId <= 0) {  // 전체조회
            cardPage = cardRepository.findAll(pageable);

        } else if (columnId > 0 && userId <= 0) { // 상태별 조회
            cardPage = cardRepository.findByColumnId(columnId, pageable);

        } else if (columnId <= 0) { // 유저별 조회
            cardPage = cardRepository.findByWorkerId(userId, pageable);
        } else {
            cardPage = cardRepository.findByColumnIdAndWorkerId(columnId, userId, pageable);
        }

        return cardPage.map(CardResponseDto::from);
    }

    public void deleteCard(Long id, UserPrincipal userPrincipal) {

        Card card = getById(id);
        String tryUsername = userPrincipal.getUser().getUsername();
        if (!Objects.equals(card.getAuthorName(), tryUsername)) {
            return;
        }

        cardRepository.deleteById(id);
    }

    public Card getById(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }
}

package com.sparta.millboard.service;

import com.sparta.millboard.dto.request.CardRequestDto;
import com.sparta.millboard.dto.response.CardResponseDto;
import com.sparta.millboard.model.BoardColumn;
import com.sparta.millboard.model.Card;
import com.sparta.millboard.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final BoardColumnService boardColumnService;
    private final UserService userService;

    public CardResponseDto createCard(CardRequestDto cardRequestDto, Long columnId) {

        BoardColumn column = boardColumnService.getById(columnId);

        Card card = cardRepository.save(cardRequestDto.toEntity());
        card.setColumn(column);

        return CardResponseDto.from(card);
    }

    @Transactional
    public CardResponseDto updateCard(CardRequestDto cardRequestDto, Long columnId, Long cardId) {

        BoardColumn column = boardColumnService.getById(columnId);

        Card card = getById(cardId);
        card.setColumn(column);
        card.update(cardRequestDto.toEntity());

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
            cardPage = cardRepository.findByColumnId(columnId,pageable);

        } else if (columnId <= 0) { // 유저별 조회
            cardPage = cardRepository.findByAuthorId(userId,pageable);
        }
        else{
            cardPage = cardRepository.findByColumnIdAndAuthorId(columnId, userId,pageable);
        }

        return cardPage.map(CardResponseDto::from);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    public Card getById(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }
}

package com.sparta.millboard.service;

import com.sparta.millboard.dto.request.CommentRequestDto;
import com.sparta.millboard.dto.response.CommentResponseDto;
import com.sparta.millboard.model.Card;
import com.sparta.millboard.model.Comment;
import com.sparta.millboard.model.User;
import com.sparta.millboard.repository.CommentRepository;
import com.sparta.millboard.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "commentService")
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardService cardService;
    private final UserService userService;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long cardId,
                                            UserPrincipal userPrincipal) {

        Card card = cardService.getById(cardId);
        User user = userPrincipal.getUser();

        Comment comment = commentRequestDto.toEntity();
        comment.setCard(card);
        comment.setAuthor(user);

        commentRepository.save(comment);

        return CommentResponseDto.from(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long commentId,
                                            UserPrincipal userPrincipal) {

        Comment comment = getById(commentId);
        Long tryUserId = userPrincipal.getUser().getId();
        if (!Objects.equals(comment.getAuthor().getId(), tryUserId)) {
            return null;
        }

        comment.update(commentRequestDto.toEntity());
        return CommentResponseDto.from(comment);
    }

    @Transactional(readOnly = true)
    public CommentResponseDto getComment(Long commentId) {
        return CommentResponseDto.from(getById(commentId));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByCardId(Long cardId) {
        List<Comment> comments = commentRepository.findAllByCardId(cardId);
        return comments.stream().map(CommentResponseDto::from).toList();
    }

    @Transactional
    public void deleteComment(Long id, UserPrincipal userPrincipal) {

        Comment comment = getById(id);
        Long tryUserId = userPrincipal.getUser().getId();
        if (!Objects.equals(comment.getAuthor().getId(), tryUserId)) {
            return;
        }

        commentRepository.deleteById(id);
    }

    public Comment getById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
    }
}

package com.onewave.careerquest.comments.service;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.comments.domain.Comment;
import com.onewave.careerquest.comments.dto.CommentCreateRequestDto;
import com.onewave.careerquest.comments.dto.CommentResponseDto;
import com.onewave.careerquest.comments.repository.CommentRepository;
import com.onewave.careerquest.exception.ResourceNotFoundException;
import com.onewave.careerquest.exception.UnauthorizedAccessException;
import com.onewave.careerquest.submissions.domain.Submission;
import com.onewave.careerquest.submissions.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final SubmissionRepository submissionRepository;

    @Transactional
    public CommentResponseDto createComment(Long submissionId, CommentCreateRequestDto requestDto, Account currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedAccessException("User must be logged in to comment.");
        }
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found with id: " + submissionId));

        Comment comment = new Comment(
                submission,
                currentUser,
                requestDto.getContent(),
                LocalDateTime.now()
        );

        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDto.from(savedComment);
    }

    public List<CommentResponseDto> getAllCommentsBySubmissionId(Long submissionId) {
        if (!submissionRepository.existsById(submissionId)) {
            throw new ResourceNotFoundException("Submission not found with id: " + submissionId);
        }
        return commentRepository.findAllBySubmissionIdOrderByCreatedAtDesc(submissionId).stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId, Account currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedAccessException("User must be logged in to delete a comment.");
        }
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        if (!Objects.equals(comment.getAuthorAccount().getId(), currentUser.getId())) {
            throw new UnauthorizedAccessException("User is not authorized to delete this comment.");
        }

        commentRepository.delete(comment);
    }
}

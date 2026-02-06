package com.onewave.careerquest.comments.controller;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.comments.dto.CommentCreateRequestDto;
import com.onewave.careerquest.comments.dto.CommentResponseDto;
import com.onewave.careerquest.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions/{submissionId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long submissionId,
            @RequestBody CommentCreateRequestDto requestDto,
            @AuthenticationPrincipal Account currentUser) {
        CommentResponseDto responseDto = commentService.createComment(submissionId, requestDto, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsBySubmissionId(@PathVariable Long submissionId) {
        List<CommentResponseDto> comments = commentService.getAllCommentsBySubmissionId(submissionId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long submissionId, // Not used in service, but good for RESTful URL structure
            @PathVariable Long commentId,
            @AuthenticationPrincipal Account currentUser) {
        commentService.deleteComment(commentId, currentUser);
        return ResponseEntity.noContent().build();
    }
}

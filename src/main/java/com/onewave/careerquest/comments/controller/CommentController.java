package com.onewave.careerquest.comments.controller;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.accounts.repository.AccountRepository;
import com.onewave.careerquest.comments.dto.CommentCreateRequestDto;
import com.onewave.careerquest.comments.dto.CommentResponseDto;
import com.onewave.careerquest.comments.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions/{submissionId}/comments")
@RequiredArgsConstructor
@Tag(name = "댓글 (Comments)", description = "제출물에 대한 댓글 관리 API")
public class CommentController {

    private final CommentService commentService;
    private final AccountRepository accountRepository;

    @Operation(summary = "댓글 생성", description = "제출물에 새로운 댓글을 작성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "댓글 생성 성공",
            content = @Content(schema = @Schema(implementation = CommentResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "제출물을 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @Parameter(description = "제출물 ID", example = "1", required = true)
            @PathVariable Long submissionId,
            @RequestBody CommentCreateRequestDto requestDto,
            @Parameter(description = "사용자 ID (로그인 시 받은 userId)", example = "1", required = true)
            @RequestParam(required = false, defaultValue = "1") Long userId) {
        Account currentUser = accountRepository.findById(userId).orElse(null);
        CommentResponseDto responseDto = commentService.createComment(submissionId, requestDto, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "댓글 목록 조회", description = "특정 제출물의 모든 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllCommentsBySubmissionId(
            @Parameter(description = "제출물 ID", example = "1", required = true)
            @PathVariable Long submissionId) {
        List<CommentResponseDto> comments = commentService.getAllCommentsBySubmissionId(submissionId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 삭제", description = "작성한 댓글을 삭제합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음"),
        @ApiResponse(responseCode = "403", description = "권한 없음 (작성자만 삭제 가능)")
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "제출물 ID", example = "1", required = true)
            @PathVariable Long submissionId,
            @Parameter(description = "댓글 ID", example = "1", required = true)
            @PathVariable Long commentId,
            @Parameter(description = "사용자 ID (로그인 시 받은 userId)", example = "1", required = true)
            @RequestParam(required = false, defaultValue = "1") Long userId) {
        Account currentUser = accountRepository.findById(userId).orElse(null);
        commentService.deleteComment(commentId, currentUser);
        return ResponseEntity.noContent().build();
    }
}

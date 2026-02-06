package com.onewave.careerquest.comments.dto;

import com.onewave.careerquest.comments.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long submissionId;
    private Long authorId;
    private String authorNickname;
    private String content;
    private LocalDateTime createdAt;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .submissionId(comment.getSubmission().getId())
                .authorId(comment.getAuthorAccount().getId())
                .authorNickname(comment.getAuthorAccount().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

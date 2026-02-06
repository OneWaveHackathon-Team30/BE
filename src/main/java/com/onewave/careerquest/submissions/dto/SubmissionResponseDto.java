package com.onewave.careerquest.submissions.dto;

import com.onewave.careerquest.submissions.domain.Submission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "제출 응답 DTO")
public class SubmissionResponseDto {
    
    @Schema(description = "제출 ID", example = "1")
    private final Long id;
    
    @Schema(description = "시나리오 ID", example = "1")
    private final Long scenarioId;
    
    @Schema(description = "제출자 계정 ID", example = "1")
    private final Long userAccountId;
    
    @Schema(description = "제출 내용", example = "이것은 제출 내용입니다.")
    private final String content;
    
    @Schema(description = "제출 일시", example = "2026-02-06T15:30:00")
    private final LocalDateTime submittedAt;
    
    @Schema(description = "채택 여부", example = "false")
    private final Boolean isAdopted;
    
    @Schema(description = "채택 일시", example = "2026-02-06T17:00:00")
    private final LocalDateTime adoptedAt;

    public SubmissionResponseDto(Submission submission) {
        this.id = submission.getId();
        this.scenarioId = submission.getScenario().getId();
        this.userAccountId = submission.getUserAccount().getId();
        this.content = submission.getContent();
        this.submittedAt = submission.getSubmittedAt();
        this.isAdopted = submission.getIsAdopted();
        this.adoptedAt = submission.getAdoptedAt();
    }
}

package com.onewave.careerquest.submissions.dto;

import com.onewave.careerquest.submissions.domain.Submission;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SubmissionResponseDto {
    private final Long id;
    private final Long scenarioId;
    private final Long userAccountId;
    private final String content;
    private final LocalDateTime submittedAt;
    private final LocalDateTime updatedAt;
    private final Boolean isAdopted;
    private final LocalDateTime adoptedAt;

    public SubmissionResponseDto(Submission submission) {
        this.id = submission.getId();
        this.scenarioId = submission.getScenario().getId();
        this.userAccountId = submission.getUserAccount().getId();
        this.content = submission.getContent();
        this.submittedAt = submission.getSubmittedAt();
        this.updatedAt = submission.getUpdatedAt();
        this.isAdopted = submission.getIsAdopted();
        this.adoptedAt = submission.getAdoptedAt();
    }
}

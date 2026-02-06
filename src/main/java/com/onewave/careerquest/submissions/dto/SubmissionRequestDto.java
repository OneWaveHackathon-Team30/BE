package com.onewave.careerquest.submissions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "제출 요청 DTO")
public class SubmissionRequestDto {
    
    @Schema(description = "제출 내용", example = "이것은 제출 내용입니다.", required = true)
    private String content;
    
    @Schema(description = "제출자 계정 ID", example = "1", required = true)
    private Long userAccountId;
}

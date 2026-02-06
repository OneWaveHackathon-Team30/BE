package com.onewave.careerquest.mypage.dto;

import com.onewave.careerquest.submissions.dto.SubmissionResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "채택된 제출 목록 응답")
public class AdoptedSubmissionsResponseDto {
    
    @Schema(description = "채택된 제출 총 개수", example = "5")
    private int adoptedCount;
    
    @Schema(description = "채택된 제출 목록")
    private List<SubmissionResponseDto> submissions;
}

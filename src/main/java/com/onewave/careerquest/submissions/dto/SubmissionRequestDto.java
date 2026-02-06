package com.onewave.careerquest.submissions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "제출 요청 DTO")
public class SubmissionRequestDto {
    
    @Schema(description = "제출 내용 (GitHub URL 또는 설명)", 
            example = "https://github.com/myusername/solution-repo\n\n구현 완료했습니다!\n- WebSocket 실시간 통신 구현\n- Redux로 상태 관리\n- Chart.js로 데이터 시각화\n- 반응형 디자인 적용", 
            required = true)
    private String content;
    
    @Schema(description = "제출자 계정 ID (로그인 시 받은 userId)", example = "1", required = true)
    private Long userAccountId;
}

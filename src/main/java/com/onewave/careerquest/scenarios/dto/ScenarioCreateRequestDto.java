package com.onewave.careerquest.scenarios.dto;

import com.onewave.careerquest.scenarios.domain.Scenario;
import com.onewave.careerquest.scenarios.domain.Source;
import com.onewave.careerquest.scenarios.domain.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "시나리오 생성 요청 DTO")
public class ScenarioCreateRequestDto {
    
    @Schema(description = "시나리오 제목", example = "React로 실시간 대시보드 구현하기", required = true)
    private String title;
    
    @Schema(description = "시나리오 설명", 
            example = "WebSocket과 React를 활용하여 실시간 데이터를 시각화하는 대시보드를 구현하세요. 요구사항: 1) 실시간 차트 업데이트 2) 반응형 디자인 3) 성능 최적화", 
            required = true)
    private String description;
    
    @Schema(description = "저장소 URL", example = "https://github.com/company/dashboard-challenge", required = true)
    private String repoUrl;
    
    @Schema(description = "마감일시", example = "2026-12-31T23:59:59", required = true)
    private LocalDateTime dueAt;
    
    @Schema(description = "시나리오 출처 (COMPANY 또는 USER)", example = "COMPANY", required = true)
    private Source source;

}

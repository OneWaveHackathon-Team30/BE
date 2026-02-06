package com.onewave.careerquest.scenario.dto;

import com.onewave.careerquest.scenario.domain.Scenario;
import com.onewave.careerquest.scenario.domain.ScenarioSource;
import com.onewave.careerquest.scenario.domain.ScenarioStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScenarioListResponseDto {
    private Long id;
    private String title;
    private String companyName;
    private ScenarioSource source;
    private ScenarioStatus status;
    private LocalDateTime dueAt;
    private long submissionsCount;

    public static ScenarioListResponseDto from(Scenario scenario) {
        // TODO: submissionsCount는 추후 Submission 엔티티와 연동하여 실제 개수를 세어야 합니다.
        return ScenarioListResponseDto.builder()
                .id(scenario.getId())
                .title(scenario.getTitle())
                .companyName(scenario.getCompany() != null ? scenario.getCompany().getNickname() : "GEMINI")
                .source(scenario.getSource())
                .status(scenario.getStatus())
                .dueAt(scenario.getDueAt())
                .submissionsCount(0) // 현재는 임시값
                .build();
    }
}
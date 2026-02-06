package com.onewave.careerquest.scenario.dto;

import com.onewave.careerquest.scenario.domain.Scenario;
import com.onewave.careerquest.scenario.domain.ScenarioSource;
import com.onewave.careerquest.scenario.domain.ScenarioStatus;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class ScenarioListResponseDto {
    private String id;
    private String title;
    private ScenarioSource source;
    private ScenarioStatus status;
    private Date dueAt;
    private long submissionsCount; // ERD에는 없지만 명세에 있는 카운트 필드

    public static ScenarioListResponseDto fromEntity(Scenario scenario) {
        return ScenarioListResponseDto.builder()
                .id(scenario.getId())
                .title(scenario.getTitle())
                .source(scenario.getSource())
                .status(scenario.getStatus())
                .dueAt(scenario.getDueAt())
                .submissionsCount(0) // 추후 제출(Submission) 도메인 구현 시 카운트 쿼리 연동 가능
                .build();
    }
}
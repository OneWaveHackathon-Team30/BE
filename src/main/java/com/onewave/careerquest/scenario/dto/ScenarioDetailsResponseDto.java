package com.onewave.careerquest.scenario.dto;

import com.onewave.careerquest.scenario.domain.Scenario;
import com.onewave.careerquest.scenario.domain.ScenarioSource;
import com.onewave.careerquest.scenario.domain.ScenarioStatus;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class ScenarioDetailsResponseDto {
    private String id;
    private String title;
    private String description;
    private String repoUrl;
    private ScenarioSource source;
    private ScenarioStatus status;
    private Date dueAt;
    private Date createdAt;

    public static ScenarioDetailsResponseDto fromEntity(Scenario scenario) {
        return ScenarioDetailsResponseDto.builder()
                .id(scenario.getId())
                .title(scenario.getTitle())
                .description(scenario.getDescription())
                .repoUrl(scenario.getRepoUrl())
                .source(scenario.getSource())
                .status(scenario.getStatus())
                .dueAt(scenario.getDueAt())
                .createdAt(scenario.getCreatedAt())
                .build();
    }
}
package com.onewave.careerquest.scenario.dto;

import com.onewave.careerquest.scenario.domain.Scenario;
import com.onewave.careerquest.scenario.domain.ScenarioSource;
import com.onewave.careerquest.scenario.domain.ScenarioStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ScenarioDetailsResponseDto {
    private Long id;
    private Long companyAccountId;
    private String companyName;
    private String title;
    private String description;
    private String repoUrl;
    private ScenarioSource source;
    private ScenarioStatus status;
    private LocalDateTime dueAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ScenarioDetailsResponseDto from(Scenario scenario) {
        return ScenarioDetailsResponseDto.builder()
                .id(scenario.getId())
                .companyAccountId(scenario.getCompany() != null ? scenario.getCompany().getId() : null)
                .companyName(scenario.getCompany() != null ? scenario.getCompany().getNickname() : "GEMINI")
                .title(scenario.getTitle())
                .description(scenario.getDescription())
                .repoUrl(scenario.getRepoUrl())
                .source(scenario.getSource())
                .status(scenario.getStatus())
                .dueAt(scenario.getDueAt())
                .createdAt(scenario.getCreatedAt())
                .updatedAt(scenario.getUpdatedAt())
                .build();
    }
}
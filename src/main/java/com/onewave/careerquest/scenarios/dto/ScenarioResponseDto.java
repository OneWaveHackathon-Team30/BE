package com.onewave.careerquest.scenarios.dto;

import com.onewave.careerquest.scenarios.domain.Scenario;
import com.onewave.careerquest.scenarios.domain.Source;
import com.onewave.careerquest.scenarios.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScenarioResponseDto {
    private Long id;
    private Source source;
    private Long companyAccountId;
    private String companyName;
    private String title;
    private String description;
    private String repoUrl;
    private LocalDateTime dueAt;
    private Status status;
    private LocalDateTime createdAt;
    private int submissionsCount;

    public static ScenarioResponseDto from(Scenario scenario) {
        return ScenarioResponseDto.builder()
                .id(scenario.getId())
                .source(scenario.getSource())
                .companyAccountId(scenario.getCompanyAccount().getId())
                .companyName(scenario.getCompanyAccount().getNickname())
                .title(scenario.getTitle())
                .description(scenario.getDescription())
                .repoUrl(scenario.getRepoUrl())
                .dueAt(scenario.getDueAt())
                .status(scenario.getStatus())
                .createdAt(scenario.getCreatedAt())
                .submissionsCount(scenario.getSubmissions().size())
                .build();
    }
}

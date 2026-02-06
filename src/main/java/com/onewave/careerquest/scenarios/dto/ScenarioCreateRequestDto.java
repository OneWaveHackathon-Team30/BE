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
public class ScenarioCreateRequestDto {
    private String title;
    private String description;
    private String repoUrl;
    private LocalDateTime dueAt;
    private Source source;

}

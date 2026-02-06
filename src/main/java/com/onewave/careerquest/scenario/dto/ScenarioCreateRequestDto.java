package com.onewave.careerquest.scenario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Date;

@Data
public class ScenarioCreateRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    private String repoUrl; // repo_url 매핑

    @NotNull(message = "마감 기한은 필수입니다.")
    private Date dueAt; // due_at 매핑
}
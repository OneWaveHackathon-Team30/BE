package com.onewave.careerquest.scenario.controller;

import com.onewave.careerquest.common.dto.GlobalResponse;
import com.onewave.careerquest.scenario.dto.ScenarioCreateRequestDto;
import com.onewave.careerquest.scenario.dto.ScenarioDetailsResponseDto;
import com.onewave.careerquest.scenario.dto.ScenarioListResponseDto;
import com.onewave.careerquest.scenario.service.ScenarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenarios")
@RequiredArgsConstructor
public class ScenarioController {

    private final ScenarioService scenarioService;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<ScenarioListResponseDto>>> getAllScenarios() {
        List<ScenarioListResponseDto> scenarios = scenarioService.getAllScenarios();
        return ResponseEntity.ok(GlobalResponse.success(200, scenarios));
    }

    @GetMapping("/{scenarioId}")
    public ResponseEntity<GlobalResponse<ScenarioDetailsResponseDto>> getScenarioById(
            @PathVariable Long scenarioId) {
        ScenarioDetailsResponseDto scenario = scenarioService.getScenarioById(scenarioId);
        return ResponseEntity.ok(GlobalResponse.success(200, scenario));
    }

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<GlobalResponse<?>> createScenario(
            @Valid @RequestBody ScenarioCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        // UserDetails의 username을 회사의 고유 이메일로 사용한다고 가정합니다.
        String companyUserEmail = userDetails.getUsername();
        scenarioService.createScenario(requestDto, companyUserEmail);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GlobalResponse.success(201, "작성이 완료되었습니다."));
    }
}
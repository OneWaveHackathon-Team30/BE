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
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/scenarios")
@RequiredArgsConstructor
public class ScenarioController {

    private final ScenarioService scenarioService;

    @GetMapping
    public Mono<ResponseEntity<GlobalResponse<List<ScenarioListResponseDto>>>> getAllScenarios() {
        return scenarioService.getAllScenarios()
                .collectList()
                .map(list -> ResponseEntity.ok(GlobalResponse.success(200, list)));
    }

    @GetMapping("/{scenarioId}")
    public Mono<ResponseEntity<GlobalResponse<ScenarioDetailsResponseDto>>> getScenarioById(
            @PathVariable String scenarioId) {
        return scenarioService.getScenarioById(scenarioId)
                .map(dto -> ResponseEntity.ok(GlobalResponse.success(200, dto)));
    }

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public Mono<ResponseEntity<GlobalResponse<String>>> createScenario(
            @Valid @RequestBody ScenarioCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        // 시나리오 작성자의 ID를 인증 객체에서 추출
        String companyAccountId = userDetails.getUsername();

        return scenarioService.createScenario(requestDto, companyAccountId)
                .map(saved -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(GlobalResponse.success(201, "시나리오 등록이 완료되었습니다.")));
    }
}
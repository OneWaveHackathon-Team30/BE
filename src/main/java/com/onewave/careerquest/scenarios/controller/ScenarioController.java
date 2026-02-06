package com.onewave.careerquest.scenarios.controller;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.scenarios.dto.ScenarioCreateRequestDto;
import com.onewave.careerquest.scenarios.dto.ScenarioResponseDto;
import com.onewave.careerquest.scenarios.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenarios")
@RequiredArgsConstructor
public class ScenarioController {

    private final ScenarioService scenarioService;

    @PostMapping
    public ResponseEntity<ScenarioResponseDto> createScenario(@RequestBody ScenarioCreateRequestDto requestDto, @AuthenticationPrincipal Account currentUser) {
        ScenarioResponseDto responseDto = scenarioService.createScenario(requestDto, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScenarioResponseDto>> getAllScenarios() {
        List<ScenarioResponseDto> scenarios = scenarioService.getAllScenarios();
        return ResponseEntity.ok(scenarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScenarioResponseDto> getScenarioById(@PathVariable Long id) {
        ScenarioResponseDto scenario = scenarioService.getScenarioById(id);
        return ResponseEntity.ok(scenario);
    }
}

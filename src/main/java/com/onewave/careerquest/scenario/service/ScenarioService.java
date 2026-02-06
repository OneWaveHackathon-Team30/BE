package com.onewave.careerquest.scenario.service;

import com.onewave.careerquest.scenario.domain.Scenario;
import com.onewave.careerquest.scenario.domain.ScenarioSource;
import com.onewave.careerquest.scenario.domain.ScenarioStatus;
import com.onewave.careerquest.scenario.dto.ScenarioCreateRequestDto;
import com.onewave.careerquest.scenario.dto.ScenarioDetailsResponseDto;
import com.onewave.careerquest.scenario.dto.ScenarioListResponseDto;
import com.onewave.careerquest.scenario.repository.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

    // 1. 최신 등록 순 전체 조회
    public Flux<ScenarioListResponseDto> getAllScenarios() {
        return scenarioRepository.findAllByOrderByCreatedAtDesc()
                .map(ScenarioListResponseDto::fromEntity);
    }

    // 2. 시나리오 상세 조회 (404 예외 처리 포함)
    public Mono<ScenarioDetailsResponseDto> getScenarioById(String id) {
        return scenarioRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("해당 시나리오를 찾을 수 없습니다. ID: " + id)))
                .map(ScenarioDetailsResponseDto::fromEntity);
    }

    // 3. 기업 사용자용 시나리오 등록
    public Mono<Scenario> createScenario(ScenarioCreateRequestDto dto, String companyAccountId) {
        Scenario scenario = Scenario.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .repoUrl(dto.getRepoUrl())
                .dueAt(dto.getDueAt())
                .companyAccountId(companyAccountId)
                .source(ScenarioSource.COMPANY)
                .status(ScenarioStatus.OPEN)
                .build();

        return scenarioRepository.save(scenario);
    }
}
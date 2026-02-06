package com.onewave.careerquest.scenario.service;

import com.onewave.careerquest.scenario.domain.Account;
import com.onewave.careerquest.scenario.domain.Scenario;
import com.onewave.careerquest.scenario.domain.ScenarioSource;
import com.onewave.careerquest.scenario.domain.ScenarioStatus;
import com.onewave.careerquest.scenario.dto.ScenarioCreateRequestDto;
import com.onewave.careerquest.scenario.dto.ScenarioDetailsResponseDto;
import com.onewave.careerquest.scenario.dto.ScenarioListResponseDto;
import com.onewave.careerquest.scenario.repository.AccountRepository;
import com.onewave.careerquest.scenario.repository.ScenarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public List<ScenarioListResponseDto> getAllScenarios() {
        List<Scenario> scenarios = scenarioRepository.findAllByOrderByCreatedAtDesc();
        return scenarios.stream()
                .map(this::checkAndUpdateStatus)
                .map(ScenarioListResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ScenarioDetailsResponseDto getScenarioById(Long scenarioId) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new NoSuchElementException("Scenario not found with id: " + scenarioId));
        checkAndUpdateStatus(scenario);
        return ScenarioDetailsResponseDto.from(scenario);
    }

    @Transactional
    public Scenario createScenario(ScenarioCreateRequestDto requestDto, String companyUserEmail) {
        // For hackathon simplicity, we'll create a dummy company account if it doesn't exist.
        // In a real app, this would come from a proper user management system.
        Account company = accountRepository.findByEmail(companyUserEmail)
                .orElseGet(() -> accountRepository.save(Account.builder()
                        .email(companyUserEmail)
                        .nickname("Dummy Company")
                        .role(com.onewave.careerquest.scenario.domain.Role.COMPANY)
                        .build()));

        Scenario scenario = Scenario.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .repoUrl(requestDto.getRepoUrl())
                .dueAt(requestDto.getDueAt())
                .company(company)
                .source(ScenarioSource.COMPANY)
                .status(ScenarioStatus.OPEN)
                .build();
        return scenarioRepository.save(scenario);
    }

    private Scenario checkAndUpdateStatus(Scenario scenario) {
        boolean isPastDue = scenario.getDueAt() != null && scenario.getDueAt().isBefore(LocalDateTime.now());
        if (scenario.getStatus() == ScenarioStatus.OPEN && isPastDue) {
            scenario.setStatus(ScenarioStatus.CLOSED);
        }
        return scenario;
    }
}
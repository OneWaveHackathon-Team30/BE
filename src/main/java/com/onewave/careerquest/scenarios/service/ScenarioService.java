package com.onewave.careerquest.scenarios.service;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.accounts.domain.Role;
import com.onewave.careerquest.accounts.repository.AccountRepository;
import com.onewave.careerquest.exception.ResourceNotFoundException;
import com.onewave.careerquest.exception.UnauthorizedAccessException;
import com.onewave.careerquest.scenarios.domain.Scenario;
import com.onewave.careerquest.scenarios.domain.Source;
import com.onewave.careerquest.scenarios.domain.Status;
import com.onewave.careerquest.scenarios.dto.ScenarioCreateRequestDto;
import com.onewave.careerquest.scenarios.dto.ScenarioResponseDto;
import com.onewave.careerquest.scenarios.repository.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;

    @Transactional
    public ScenarioResponseDto createScenario(ScenarioCreateRequestDto requestDto, Account currentUser) {
        if (currentUser == null) {
            throw new UnauthorizedAccessException("User must be logged in to create a scenario.");
        }
        if (currentUser.getRole() != Role.COMPANY) {
            throw new UnauthorizedAccessException("Only company accounts can create scenarios.");
        }

        Scenario scenario = new Scenario(
                requestDto.getSource(),
                currentUser,
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getRepoUrl(),
                requestDto.getDueAt(),
                Status.OPEN,
                LocalDateTime.now()
        );

        Scenario savedScenario = scenarioRepository.save(scenario);
        return ScenarioResponseDto.from(savedScenario);
    }

    public List<ScenarioResponseDto> getAllScenarios() {
        return scenarioRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(ScenarioResponseDto::from)
                .collect(Collectors.toList());
    }

    public ScenarioResponseDto getScenarioById(Long id) {
        Scenario scenario = scenarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scenario not found with id: " + id));
        return ScenarioResponseDto.from(scenario);
    }
}

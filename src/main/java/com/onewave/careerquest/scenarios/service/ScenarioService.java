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
    private final AccountRepository accountRepository;

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

    public List<ScenarioResponseDto> getAllScenarios(String authorizationHeader) {
        String uid = extractUidFromHeader(authorizationHeader);
        Account currentUser = accountRepository.findByUid(uid)
                .orElseThrow(() -> new UnauthorizedAccessException("Invalid user UID."));

        return scenarioRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(ScenarioResponseDto::from)
                .collect(Collectors.toList());
    }

    public ScenarioResponseDto getScenarioById(Long id, String authorizationHeader) {
        Scenario scenario = scenarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scenario not found with id: " + id));

        // Assuming "Bearer guest" is for guest users or users who are not logged in
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && !authorizationHeader.substring(7).equals("guest")) {
            String uid = extractUidFromHeader(authorizationHeader);
            Account currentUser = accountRepository.findByUid(uid)
                    .orElseThrow(() -> new UnauthorizedAccessException("Invalid user UID."));

            // If the user is a company, they can only see their own scenarios
            if (currentUser.getRole() == Role.COMPANY && !scenario.getCompanyAccount().equals(currentUser)) {
                throw new UnauthorizedAccessException("You are not authorized to view this scenario.");
            }
        } else {
            // This part handles the logic for guest users.
            // If you want to prevent guests from seeing any scenarios, you can throw an exception here.
            // For now, we allow guests to see the scenario.
        }

        return ScenarioResponseDto.from(scenario);
    }

    private String extractUidFromHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedAccessException("Authorization header is missing or invalid.");
        }
        return authorizationHeader.substring(7);
    }
}

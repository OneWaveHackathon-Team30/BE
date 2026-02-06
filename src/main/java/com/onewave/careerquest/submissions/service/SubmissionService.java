package com.onewave.careerquest.submissions.service;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.accounts.repository.AccountRepository;
import com.onewave.careerquest.scenarios.domain.Scenario;
import com.onewave.careerquest.scenarios.repository.ScenarioRepository;
import com.onewave.careerquest.submissions.domain.Submission;
import com.onewave.careerquest.submissions.dto.SubmissionRequestDto;
import com.onewave.careerquest.submissions.dto.SubmissionResponseDto;
import com.onewave.careerquest.submissions.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ScenarioRepository scenarioRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public SubmissionResponseDto createSubmission(Long scenarioId, SubmissionRequestDto requestDto) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new IllegalArgumentException("Scenario not found with id: " + scenarioId));

        Account userAccount = accountRepository.findById(requestDto.getUserAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + requestDto.getUserAccountId()));

        Submission submission = new Submission(
                scenario,
                userAccount,
                requestDto.getContent(),
                LocalDateTime.now()
        );

        Submission savedSubmission = submissionRepository.save(submission);
        return new SubmissionResponseDto(savedSubmission);
    }

    public List<SubmissionResponseDto> getSubmissionsByScenario(Long scenarioId) {
        if (!scenarioRepository.existsById(scenarioId)) {
            throw new IllegalArgumentException("Scenario not found with id: " + scenarioId);
        }

        return submissionRepository.findByScenarioId(scenarioId)
                .stream()
                .map(SubmissionResponseDto::new)
                .collect(Collectors.toList());
    }

    public SubmissionResponseDto getSubmissionById(Long scenarioId, Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with id: " + submissionId));

        if (!submission.getScenario().getId().equals(scenarioId)) {
            throw new IllegalArgumentException("Submission does not belong to scenario: " + scenarioId);
        }

        return new SubmissionResponseDto(submission);
    }

    @Transactional
    public SubmissionResponseDto adoptSubmission(Long scenarioId, Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with id: " + submissionId));

        if (!submission.getScenario().getId().equals(scenarioId)) {
            throw new IllegalArgumentException("Submission does not belong to scenario: " + scenarioId);
        }

        if (submission.getIsAdopted()) {
            throw new IllegalArgumentException("Submission is already adopted");
        }

        submission.setIsAdopted(true);
        submission.setAdoptedAt(LocalDateTime.now());

        Submission savedSubmission = submissionRepository.save(submission);
        return new SubmissionResponseDto(savedSubmission);
    }

    @Transactional
    public void deleteSubmission(Long scenarioId, Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with id: " + submissionId));

        if (!submission.getScenario().getId().equals(scenarioId)) {
            throw new IllegalArgumentException("Submission does not belong to scenario: " + scenarioId);
        }

        submissionRepository.delete(submission);
    }
}

package com.onewave.careerquest.submissions.controller;

import com.onewave.careerquest.submissions.dto.SubmissionRequestDto;
import com.onewave.careerquest.submissions.dto.SubmissionResponseDto;
import com.onewave.careerquest.submissions.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenarios/{scenarioId}/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<SubmissionResponseDto> createSubmission(
            @PathVariable Long scenarioId,
            @RequestBody SubmissionRequestDto requestDto) {
        SubmissionResponseDto response = submissionService.createSubmission(scenarioId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissions(
            @PathVariable Long scenarioId) {
        List<SubmissionResponseDto> submissions = submissionService.getSubmissionsByScenario(scenarioId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionResponseDto> getSubmission(
            @PathVariable Long scenarioId,
            @PathVariable Long submissionId) {
        SubmissionResponseDto submission = submissionService.getSubmissionById(scenarioId, submissionId);
        return ResponseEntity.ok(submission);
    }

    @PostMapping("/{submissionId}/adopt")
    public ResponseEntity<SubmissionResponseDto> adoptSubmission(
            @PathVariable Long scenarioId,
            @PathVariable Long submissionId) {
        SubmissionResponseDto submission = submissionService.adoptSubmission(scenarioId, submissionId);
        return ResponseEntity.ok(submission);
    }

    @DeleteMapping("/{submissionId}")
    public ResponseEntity<Void> deleteSubmission(
            @PathVariable Long scenarioId,
            @PathVariable Long submissionId) {
        submissionService.deleteSubmission(scenarioId, submissionId);
        return ResponseEntity.noContent().build();
    }
}

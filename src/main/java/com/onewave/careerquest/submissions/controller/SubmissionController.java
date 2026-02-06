package com.onewave.careerquest.submissions.controller;

import com.onewave.careerquest.submissions.dto.SubmissionRequestDto;
import com.onewave.careerquest.submissions.dto.SubmissionResponseDto;
import com.onewave.careerquest.submissions.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Submissions", description = "시나리오 제출 관리 API")
@RestController
@RequestMapping("/api/scenarios/{scenarioId}/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @Operation(summary = "제출 생성", description = "특정 시나리오에 대한 새로운 제출을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "제출 생성 성공",
                    content = @Content(schema = @Schema(implementation = SubmissionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "404", description = "시나리오를 찾을 수 없음", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SubmissionResponseDto> createSubmission(
            @Parameter(description = "시나리오 ID", required = true) @PathVariable Long scenarioId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "제출 요청 정보", required = true)
            @RequestBody SubmissionRequestDto requestDto) {
        SubmissionResponseDto response = submissionService.createSubmission(scenarioId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "제출 목록 조회", description = "특정 시나리오의 모든 제출 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SubmissionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "시나리오를 찾을 수 없음", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<SubmissionResponseDto>> getSubmissions(
            @Parameter(description = "시나리오 ID", required = true) @PathVariable Long scenarioId) {
        List<SubmissionResponseDto> submissions = submissionService.getSubmissionsByScenario(scenarioId);
        return ResponseEntity.ok(submissions);
    }

    @Operation(summary = "제출 상세 조회", description = "특정 제출의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = SubmissionResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "제출을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/{submissionId}")
    public ResponseEntity<SubmissionResponseDto> getSubmission(
            @Parameter(description = "시나리오 ID", required = true) @PathVariable Long scenarioId,
            @Parameter(description = "제출 ID", required = true) @PathVariable Long submissionId) {
        SubmissionResponseDto submission = submissionService.getSubmissionById(scenarioId, submissionId);
        return ResponseEntity.ok(submission);
    }

    @Operation(summary = "제출 채택", description = "특정 제출을 채택합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "채택 성공",
                    content = @Content(schema = @Schema(implementation = SubmissionResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "이미 채택된 제출", content = @Content),
            @ApiResponse(responseCode = "404", description = "제출을 찾을 수 없음", content = @Content)
    })
    @PostMapping("/{submissionId}/adopt")
    public ResponseEntity<SubmissionResponseDto> adoptSubmission(
            @Parameter(description = "시나리오 ID", required = true) @PathVariable Long scenarioId,
            @Parameter(description = "제출 ID", required = true) @PathVariable Long submissionId) {
        SubmissionResponseDto submission = submissionService.adoptSubmission(scenarioId, submissionId);
        return ResponseEntity.ok(submission);
    }

    @Operation(summary = "제출 삭제", description = "특정 제출을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "제출을 찾을 수 없음", content = @Content)
    })
    @DeleteMapping("/{submissionId}")
    public ResponseEntity<Void> deleteSubmission(
            @Parameter(description = "시나리오 ID", required = true) @PathVariable Long scenarioId,
            @Parameter(description = "제출 ID", required = true) @PathVariable Long submissionId) {
        submissionService.deleteSubmission(scenarioId, submissionId);
        return ResponseEntity.noContent().build();
    }
}

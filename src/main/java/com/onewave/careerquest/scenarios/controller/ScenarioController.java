package com.onewave.careerquest.scenarios.controller;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.accounts.repository.AccountRepository;
import com.onewave.careerquest.scenarios.dto.ScenarioCreateRequestDto;
import com.onewave.careerquest.scenarios.dto.ScenarioResponseDto;
import com.onewave.careerquest.scenarios.service.ScenarioService;
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

@RestController
@RequestMapping("/api/scenarios")
@RequiredArgsConstructor
@Tag(name = "시나리오 (Scenarios)", description = "채용 과제 시나리오 관리 API")
public class ScenarioController {

    private final ScenarioService scenarioService;
    private final AccountRepository accountRepository;

    @Operation(summary = "시나리오 생성", 
               description = "새로운 채용 과제 시나리오를 생성합니다. userId는 로그인 시 받은 사용자 ID입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "시나리오 생성 성공",
            content = @Content(schema = @Schema(implementation = ScenarioResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @ApiResponse(responseCode = "403", description = "권한 없음 (COMPANY 계정만 생성 가능)")
    })
    @PostMapping
    public ResponseEntity<ScenarioResponseDto> createScenario(
            @RequestBody ScenarioCreateRequestDto requestDto,
            @Parameter(description = "사용자 ID (로그인 시 받은 userId)", example = "1", required = true)
            @RequestParam(required = false, defaultValue = "1") Long userId) {
        Account currentUser = accountRepository.findById(userId).orElse(null);
        ScenarioResponseDto responseDto = scenarioService.createScenario(requestDto, currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "모든 시나리오 조회", description = "등록된 모든 시나리오 목록을 최신순으로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<ScenarioResponseDto>> getAllScenarios() {
        List<ScenarioResponseDto> scenarios = scenarioService.getAllScenarios();
        return ResponseEntity.ok(scenarios);
    }

    @Operation(summary = "특정 시나리오 조회", description = "ID로 특정 시나리오의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "404", description = "시나리오를 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ScenarioResponseDto> getScenarioById(
            @Parameter(description = "시나리오 ID", example = "1", required = true)
            @PathVariable Long id) {
        ScenarioResponseDto scenario = scenarioService.getScenarioById(id);
        return ResponseEntity.ok(scenario);
    }
}

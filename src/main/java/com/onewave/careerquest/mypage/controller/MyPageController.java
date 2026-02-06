package com.onewave.careerquest.mypage.controller;

import com.onewave.careerquest.mypage.dto.AdoptedSubmissionsResponseDto;
import com.onewave.careerquest.mypage.service.MyPageService;
import com.onewave.careerquest.scenarios.dto.ScenarioResponseDto;
import com.onewave.careerquest.submissions.dto.SubmissionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
@Tag(name = "마이페이지 (MyPage)", description = "사용자 개인 정보 조회 API")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "내 제출 목록 조회", 
               description = "로그인한 사용자가 제출한 모든 솔루션 목록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = SubmissionResponseDto.class)))
    })
    @GetMapping("/submissions")
    public ResponseEntity<List<SubmissionResponseDto>> getMySubmissions(
            @Parameter(description = "사용자 ID (로그인 시 받은 userId)", example = "1", required = true)
            @RequestParam Long userId) {
        List<SubmissionResponseDto> submissions = myPageService.getMySubmissions(userId);
        return ResponseEntity.ok(submissions);
    }

    @Operation(summary = "채택된 제출 목록 조회", 
               description = "채택된 제출의 개수와 목록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = AdoptedSubmissionsResponseDto.class)))
    })
    @GetMapping("/submissions/adopted")
    public ResponseEntity<AdoptedSubmissionsResponseDto> getAdoptedSubmissions(
            @Parameter(description = "사용자 ID (로그인 시 받은 userId)", example = "1", required = true)
            @RequestParam Long userId) {
        AdoptedSubmissionsResponseDto response = myPageService.getAdoptedSubmissions(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내가 작성한 시나리오 목록 조회", 
               description = "회사 계정이 작성한 시나리오(문제) 목록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = ScenarioResponseDto.class)))
    })
    @GetMapping("/scenarios")
    public ResponseEntity<List<ScenarioResponseDto>> getMyScenarios(
            @Parameter(description = "사용자 ID (로그인 시 받은 userId)", example = "1", required = true)
            @RequestParam Long userId) {
        List<ScenarioResponseDto> scenarios = myPageService.getMyScenarios(userId);
        return ResponseEntity.ok(scenarios);
    }
}

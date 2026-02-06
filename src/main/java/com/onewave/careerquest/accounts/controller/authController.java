package com.onewave.careerquest.accounts.controller;

import com.onewave.careerquest.accounts.DTO.LoginRequestDto;
import com.onewave.careerquest.accounts.service.authService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "인증 (Authentication)", description = "회원가입 및 로그인 API")
public class authController {

    private final authService authService;
    
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임으로 새 계정을 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입 성공",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\"message\":\"회원가입 성공\",\"userId\":1,\"email\":\"user@test.com\"}"))),
        @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "회원가입 정보",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"email\":\"user@test.com\",\"password\":\"password123\",\"nickname\":\"홍길동\"}")
                )
            )
            @RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String nickname = request.get("nickname");
        
        Long userId = authService.register(email, password, nickname);
        
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("email", email);
        response.put("message", "회원가입 성공");
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\"message\":\"로그인 성공\",\"userId\":1,\"email\":\"user@test.com\"}"))),
        @ApiResponse(responseCode = "401", description = "인증 실패",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = "{\"message\":\"이메일 또는 비밀번호가 올바르지 않습니다\"}")))
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "로그인 정보",
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"email\":\"user@test.com\",\"password\":\"password123\"}")
                )
            )
            @RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        
        Long userId = authService.login(email, password);
        
        if (userId == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "이메일 또는 비밀번호가 올바르지 않습니다");
            return ResponseEntity.status(401).body(errorResponse);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("email", email);
        response.put("message", "로그인 성공");
        
        return ResponseEntity.ok(response);
    }
}


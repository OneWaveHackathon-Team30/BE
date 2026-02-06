package com.onewave.careerquest.accounts.controller;

import com.onewave.careerquest.accounts.DTO.LoginRequestDto;
import com.onewave.careerquest.accounts.service.authService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "auth")
public class authController {

    private final authService authService;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> request) {
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
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
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


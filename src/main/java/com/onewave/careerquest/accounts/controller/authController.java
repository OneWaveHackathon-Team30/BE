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
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String email) {
        Long userId = authService.simpleLogin(email);
        
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("email", email);
        response.put("message", "로그인 성공");
        
        return ResponseEntity.ok(response);
    }
}


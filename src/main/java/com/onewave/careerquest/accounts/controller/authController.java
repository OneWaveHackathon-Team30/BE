package com.onewave.careerquest.accounts.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.onewave.careerquest.accounts.DTO.LoginRequestDto;
import com.onewave.careerquest.accounts.service.authService;
import com.onewave.careerquest.jwt.AuthTokens;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<LoginRequestDto> login(@RequestHeader("Authorization") String firebaseIdToken,
                                                 @RequestParam(required = false) String nickname) throws FirebaseAuthException {
        // 헤더에서 "Bearer <idToken>" 형태로 들어온 경우 처리
        String idToken = firebaseIdToken.replace("Bearer ", "");
        System.out.println("idToken = " + idToken);
//
//        AuthTokens tokens = authService.loginOrRegister(idToken, nickname);
//        System.out.println("nickname = " + nickname);

        // body 구성
        LoginRequestDto body = new LoginRequestDto(200, "로그인 상태 확인 성공");

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + idToken)
                .body(body);
    }
}


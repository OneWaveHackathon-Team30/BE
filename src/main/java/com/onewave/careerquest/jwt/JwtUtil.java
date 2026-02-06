package com.onewave.careerquest.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import static com.onewave.careerquest.jwt.AuthTokensGenerator.ACCESS_TOKEN_EXPIRATION_TIME;
import static com.onewave.careerquest.jwt.AuthTokensGenerator.REFRESH_TOKEN_EXPIRATION_TIME;


@Component
public class JwtUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Key key;

    public JwtUtil(@Value("${custom.jwt.secretKey}") String secretKey) {
        logger.info("JWT Secret Key 초기화 시작");
        
        if (secretKey == null || secretKey.trim().isEmpty()) {
            logger.error("JWT_SECRET 환경 변수가 설정되지 않았거나 비어있습니다.");
            throw new IllegalArgumentException("JWT_SECRET 환경 변수가 필요합니다.");
        }
        
        logger.info("JWT Secret Key 길이: {}", secretKey.length());
        
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            logger.info("Base64 디코딩 성공. 바이트 길이: {}", keyBytes.length);
            
            if (keyBytes.length < 32) {
                logger.error("JWT Secret Key가 너무 짧습니다. 최소 256비트(32바이트) 필요. 현재: {}바이트", keyBytes.length);
                throw new IllegalArgumentException("JWT Secret Key는 최소 256비트(32바이트) 이상이어야 합니다.");
            }
            
            this.key = Keys.hmacShaKeyFor(keyBytes);
            logger.info("JWT Secret Key 초기화 완료");
        } catch (IllegalArgumentException e) {
            logger.error("Base64 디코딩 실패 또는 키 생성 실패: {}", e.getMessage());
            throw new IllegalArgumentException("JWT_SECRET이 올바른 Base64 형식이 아니거나 길이가 부족합니다: " + e.getMessage(), e);
        }
    }

    public String accessTokenGenerate(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)  // id
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public String refreshTokenGenerate(String subject,Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)  // id
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public AuthTokens refreshAccessToken(String refreshToken) {
        System.out.println("1. Refresh Token 검증");
        Claims claims = parseClaims(refreshToken);
        String userId = claims.getSubject();

        if (userId == null) {
            throw new RuntimeException("유효하지 않은 refresh token입니다.");
        }

        System.out.println("2. AuthTokensGenerator 사용하여 Access Token 재발급");
        AuthTokens newTokens = generate(userId);

        // return AuthTokens.of(newTokens.getAccessToken(), refreshToken, BEARER_TOKEN, ACCESS_TOKEN_EXPIRATION_TIME);
        return AuthTokens.of(newTokens.getAccessToken(), refreshToken);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public AuthTokens generate(String id) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresAt = new Date(now + ACCESS_TOKEN_EXPIRATION_TIME);
        Date refreshTokenExpiresAt = new Date(now + REFRESH_TOKEN_EXPIRATION_TIME);

        String accessToken = accessTokenGenerate(id, accessTokenExpiresAt);
        String refreshToken = accessTokenGenerate(id, refreshTokenExpiresAt);

        return AuthTokens.of(accessToken, refreshToken);
    }
}

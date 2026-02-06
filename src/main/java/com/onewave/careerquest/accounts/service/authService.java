package com.onewave.careerquest.accounts.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.accounts.domain.Role;
import com.onewave.careerquest.accounts.repository.AccountRepository;
import com.onewave.careerquest.jwt.AuthTokens;
import com.onewave.careerquest.jwt.AuthTokensGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class authService {

    private final AccountRepository accountRepository;
    private final AuthTokensGenerator authTokensGenerator;


    /**
     * 회원가입
     */
    public AuthTokens loginOrRegister(String idToken, String name) throws FirebaseAuthException {
        // 1. Firebase ID Token 검증 및 UID 추출
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String firebaseUid = decodedToken.getUid();
        String firebaseEmail = decodedToken.getEmail();
        String nickname = "익명";
        if (name == null || name.isEmpty()) {
            nickname = firebaseEmail.split("@")[0]; // 이메일 앞부분을 닉네임으로 사용
        } else {
            nickname = name;
        }

        String finalNickname = nickname;
        Account account = accountRepository.findByUid(firebaseUid)
                .orElseGet(() -> { // 신규 회원가입
                    Account newAccount = new Account(
                            firebaseEmail,
                            firebaseUid,
                            finalNickname,
                            null, // passwordHash는 firebase에서 관리
                            Role.USER,
                            LocalDateTime.now() );
                    return accountRepository.save(newAccount);
                });
        // JWT 토큰 발급 (subject = uid)
        return authTokensGenerator.generate(account.getUid());
    }
}

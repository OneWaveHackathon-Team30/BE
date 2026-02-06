package com.onewave.careerquest.accounts.service;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.accounts.domain.Role;
import com.onewave.careerquest.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class authService {

    private final AccountRepository accountRepository;

    public Long simpleLogin(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseGet(() -> {
                    Account newAccount = new Account(
                            email,
                            "uid_" + email,
                            email.split("@")[0],
                            null,
                            Role.USER,
                            LocalDateTime.now()
                    );
                    return accountRepository.save(newAccount);
                });
        
        return account.getId();
    }
}

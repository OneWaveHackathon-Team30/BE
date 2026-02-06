package com.onewave.careerquest.accounts.service;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.accounts.domain.Role;
import com.onewave.careerquest.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class authService {

    private final AccountRepository accountRepository;

    public Long register(String email, String password, String nickname) {
        return register(email, password, nickname, null);
    }
    
    public Long register(String email, String password, String nickname, String roleStr) {
        Optional<Account> existing = accountRepository.findByEmail(email);
        if (existing.isPresent()) {
            return existing.get().getId();
        }
        
        String finalNickname = (nickname != null && !nickname.isEmpty()) 
            ? nickname 
            : email.split("@")[0];
        
        Role role = Role.USER;
        if (roleStr != null && roleStr.equalsIgnoreCase("COMPANY")) {
            role = Role.COMPANY;
        }
        
        Account newAccount = new Account(
                email,
                "uid_" + email,
                finalNickname,
                password,
                role,
                LocalDateTime.now()
        );
        Account saved = accountRepository.save(newAccount);
        return saved.getId();
    }
    
    public Long login(String email, String password) {
        Optional<Account> accountOpt = accountRepository.findByEmail(email);
        if (accountOpt.isEmpty()) {
            return null;
        }
        
        Account account = accountOpt.get();
        if (account.getPasswordHash() == null || !account.getPasswordHash().equals(password)) {
            return null;
        }
        
        return account.getId();
    }
}

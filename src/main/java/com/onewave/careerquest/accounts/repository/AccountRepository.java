package com.onewave.careerquest.accounts.repository;

import com.onewave.careerquest.accounts.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {// Firebase UID로 사용자 조회 (회원가입 여부 확인용)
    Optional<Account> findByEmail(String email);
    Optional<Account> findByUid(String uid);
}

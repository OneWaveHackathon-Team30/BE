package com.onewave.careerquest.scenario.domain;

import com.onewave.careerquest.common.Auditable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "accounts")
public class Account extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String uid;

    private String nickname;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Account(String email, String uid, String nickname, String passwordHash, Role role) {
        this.email = email;
        this.uid = uid;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.role = role;
    }
}

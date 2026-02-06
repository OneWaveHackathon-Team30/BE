// File: src/main/java/com/onewave/careerquest/accounts/domain/Account.java
package com.onewave.careerquest.accounts.domain;

import com.onewave.careerquest.scenarios.domain.Scenario;
import com.onewave.careerquest.submissions.domain.Submission;
import com.onewave.careerquest.comments.domain.Comment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "account", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    private String uid;

    @Setter
    @Column(columnDefinition = "text")
    private String nickname;

    @Setter
    @Column(name = "password_hash")
    private String passwordHash;

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "companyAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scenario> scenarios = new ArrayList<>();

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Submission> submissions = new ArrayList<>();

    @OneToMany(mappedBy = "authorAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    protected Account() {}

    // 생성자 / getter / setter (간결하게 필요할 경우 확장)
    public Account(String email, String uid, String nickname, String passwordHash, Role role, LocalDateTime createdAt) {
        this.email = email;
        this.uid = uid;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
    }

}
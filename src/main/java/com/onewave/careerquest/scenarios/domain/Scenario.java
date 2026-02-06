// File: src/main/java/com/onewave/careerquest/scenarios/domain/Scenario.java
package com.onewave.careerquest.scenarios.domain;

import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.submissions.domain.Submission;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "scenario")
public class Scenario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private Source source;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_account_id", nullable = false)
    private Account companyAccount;

    @Setter
    private String title;

    @Setter
    @Column(columnDefinition = "text")
    private String description;

    @Setter
    @Column(name = "repo_url")
    private String repoUrl;

    @Setter
    @Column(name = "due_at")
    private LocalDateTime dueAt;

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "scenario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Submission> submissions = new ArrayList<>();

    protected Scenario() {}

    public Scenario(Source source, Account companyAccount, String title, String description, String repoUrl, LocalDateTime dueAt, Status status, LocalDateTime createdAt) {
        this.source = source;
        this.companyAccount = companyAccount;
        this.title = title;
        this.description = description;
        this.repoUrl = repoUrl;
        this.dueAt = dueAt;
        this.status = status;
        this.createdAt = createdAt;
    }

}
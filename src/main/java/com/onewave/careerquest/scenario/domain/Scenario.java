package com.onewave.careerquest.scenario.domain;

import com.onewave.careerquest.common.Auditable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "scenarios")
public class Scenario extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScenarioSource source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_account_id")
    private Account company;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    private String repoUrl;

    private LocalDateTime dueAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScenarioStatus status;


    @Builder
    public Scenario(ScenarioSource source, Account company, String title, String description, String repoUrl, LocalDateTime dueAt, ScenarioStatus status) {
        this.source = source;
        this.company = company;
        this.title = title;
        this.description = description;
        this.repoUrl = repoUrl;
        this.dueAt = dueAt;
        this.status = status;
    }
}
package com.onewave.careerquest.submissions.domain;

import com.onewave.careerquest.scenarios.domain.Scenario;
import com.onewave.careerquest.accounts.domain.Account;
import com.onewave.careerquest.comments.domain.Comment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "submission")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id", nullable = false)
    private Scenario scenario;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private Account userAccount;

    @Setter
    @Column(columnDefinition = "text")
    private String content;

    @Setter
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Setter
    @Column(name = "is_adopted")
    private Boolean isAdopted = false;

    @Setter
    @Column(name = "adopted_at")
    private LocalDateTime adoptedAt;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    protected Submission() {}

    public Submission(Scenario scenario, Account userAccount, String content, LocalDateTime submittedAt) {
        this.scenario = scenario;
        this.userAccount = userAccount;
        this.content = content;
        this.submittedAt = submittedAt;
        this.isAdopted = false;
    }
}

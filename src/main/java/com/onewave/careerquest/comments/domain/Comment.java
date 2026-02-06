// File: src/main/java/com/onewave/careerquest/comments/domain/Comment.java
package com.onewave.careerquest.comments.domain;

import com.onewave.careerquest.submissions.domain.Submission;
import com.onewave.careerquest.accounts.domain.Account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_account_id", nullable = false)
    private Account authorAccount;

    @Setter
    @Column(columnDefinition = "text")
    private String content;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected Comment() {}

    public Comment(Submission submission, Account authorAccount, String content, LocalDateTime createdAt) {
        this.submission = submission;
        this.authorAccount = authorAccount;
        this.content = content;
        this.createdAt = createdAt;
    }

}
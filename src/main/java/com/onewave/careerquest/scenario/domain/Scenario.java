package com.onewave.careerquest.scenario.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collectionName = "scenarios")
public class Scenario {

    @DocumentId
    private String id; // Firestore 전용 문서 ID

    private ScenarioSource source; // COMPANY 또는 GEMINI

    private String companyAccountId; // 기업 계정과의 연관 관계 (FK)

    private String title; // 과제 제목

    private String description; // 상세 설명

    private String repoUrl; // 관련 GitHub Repository URL

    private Date dueAt; // 마감 기한

    private ScenarioStatus status; // OPEN, PENDING, CLOSED 상태

    @ServerTimestamp
    private Date createdAt; // 생성 시점 (Firestore 서버 시간)

    @Builder
    public Scenario(ScenarioSource source, String companyAccountId, String title,
                    String description, String repoUrl, Date dueAt, ScenarioStatus status) {
        this.source = source;
        this.companyAccountId = companyAccountId;
        this.title = title;
        this.description = description;
        this.repoUrl = repoUrl;
        this.dueAt = dueAt;
        this.status = status;
    }

    // 마감 여부에 따른 상태 변경
    public void closeScenario() {
        this.status = ScenarioStatus.CLOSED;
    }
}
package com.onewave.careerquest.scenario.domain;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import com.google.cloud.spring.data.firestore.Document;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collectionName = "accounts")
public class Account {

    @DocumentId
    private String id; // Firestore는 String ID를 사용하므로 bigint 대신 String을 사용합니다.

    private String email;
    private String uid; // Firebase Auth UID
    private String nickname;
    private String passwordHash;
    private Role role;

    @ServerTimestamp
    private Date createdAt;

    @ServerTimestamp
    private Date updatedAt;

    @Builder
    public Account(String email, String uid, String nickname, String passwordHash, Role role) {
        this.email = email;
        this.uid = uid;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.role = role;
    }
}

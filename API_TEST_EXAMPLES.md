# API 테스트 예시 모음

## 1. 인증 (Auth)

### 회원가입
```bash
curl -X POST "http://localhost:8080/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user1@test.com",
    "password": "password123",
    "nickname": "사용자1"
  }'
```

**응답:**
```json
{
  "message": "회원가입 성공",
  "userId": 1,
  "email": "user1@test.com"
}
```

### 로그인
```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user1@test.com",
    "password": "password123"
  }'
```

**응답:**
```json
{
  "message": "로그인 성공",
  "userId": 1,
  "email": "user1@test.com"
}
```

---

## 2. 시나리오 (Scenarios)

### 시나리오 생성
**⚠️ userId를 쿼리 파라미터로 전달**

```bash
curl -X POST "http://localhost:8080/api/scenarios?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "source": "COMPANY",
    "title": "React 대시보드 개발",
    "description": "실시간 데이터 모니터링 대시보드를 React로 구현하세요",
    "repoUrl": "https://github.com/company/dashboard-challenge",
    "dueAt": "2026-12-31T23:59:59"
  }'
```

### 시나리오 목록 조회
```bash
curl -X GET "http://localhost:8080/api/scenarios"
```

### 특정 시나리오 조회
```bash
curl -X GET "http://localhost:8080/api/scenarios/1"
```

---

## 3. 제출 (Submissions)

### 제출 생성
**⚠️ userAccountId를 JSON body에 포함**

```bash
curl -X POST "http://localhost:8080/api/scenarios/1/submissions" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "https://github.com/myrepo/solution - 완료했습니다!",
    "userAccountId": 1
  }'
```

### 특정 시나리오의 제출 목록 조회
```bash
curl -X GET "http://localhost:8080/api/scenarios/1/submissions"
```

### 특정 제출 조회
```bash
curl -X GET "http://localhost:8080/api/scenarios/1/submissions/1"
```

### 제출 채택
```bash
curl -X POST "http://localhost:8080/api/scenarios/1/submissions/1/adopt"
```

### 제출 삭제
```bash
curl -X DELETE "http://localhost:8080/api/scenarios/1/submissions/1"
```

---

## 4. 댓글 (Comments)

### 댓글 생성
**⚠️ userId를 쿼리 파라미터로 전달**

```bash
curl -X POST "http://localhost:8080/api/submissions/1/comments?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "body": "정말 훌륭한 솔루션입니다! 특히 성능 최적화 부분이 인상적이네요."
  }'
```

### 특정 제출의 댓글 목록 조회
```bash
curl -X GET "http://localhost:8080/api/submissions/1/comments"
```

### 댓글 삭제
**⚠️ userId를 쿼리 파라미터로 전달**

```bash
curl -X DELETE "http://localhost:8080/api/submissions/1/comments/1?userId=1"
```

---

## 📋 전체 시나리오 테스트

### 1단계: 사용자 생성
```bash
# 회사 계정
curl -X POST "http://localhost:8080/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "company@test.com",
    "password": "comp123",
    "nickname": "구글코리아"
  }'

# 개발자 계정
curl -X POST "http://localhost:8080/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "dev@test.com",
    "password": "dev123",
    "nickname": "개발자김씨"
  }'
```

### 2단계: 시나리오 생성 (회사)
```bash
curl -X POST "http://localhost:8080/api/scenarios?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "source": "COMPANY",
    "title": "실시간 채팅 시스템 구축",
    "description": "WebSocket을 활용한 실시간 채팅 시스템을 구현하세요",
    "repoUrl": "https://github.com/company/chat-challenge",
    "dueAt": "2026-12-31T23:59:59"
  }'
```

### 3단계: 솔루션 제출 (개발자)
```bash
curl -X POST "http://localhost:8080/api/scenarios/1/submissions" \
  -H "Content-Type: application/json" \
  -d '{
    "content": "https://github.com/dev/chat-solution\n\nWebSocket + Redis Pub/Sub으로 구현했습니다.\n- 동시 접속자 1000명 테스트 완료\n- 메시지 전송 지연 50ms 이하",
    "userAccountId": 2
  }'
```

### 4단계: 댓글 작성 (회사)
```bash
curl -X POST "http://localhost:8080/api/submissions/1/comments?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "body": "Redis 활용이 인상적입니다. 확장성도 고려되어 있어 좋네요!"
  }'
```

### 5단계: 제출 채택
```bash
curl -X POST "http://localhost:8080/api/scenarios/1/submissions/1/adopt"
```

---

## 🔍 userId 전달 방법 정리

| API | userId 전달 방법 |
|-----|-----------------|
| 시나리오 생성 | `?userId=1` (쿼리 파라미터) |
| 제출 생성 | `"userAccountId": 1` (JSON body) |
| 댓글 생성 | `?userId=1` (쿼리 파라미터) |
| 댓글 삭제 | `?userId=1` (쿼리 파라미터) |

---

## ⚠️ 주의사항

1. **시나리오 생성**: Role이 COMPANY인 계정만 가능 (서비스 로직에서 체크)
2. **비밀번호**: 평문 저장 (MVP 간소화)
3. **인증**: 모든 API가 인증 없이 접근 가능
4. **userId**: 로그인 응답에서 받은 userId를 사용

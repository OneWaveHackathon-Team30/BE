# CareerQuest MVP Demo Guide

## 🚀 실행 완료!

애플리케이션이 성공적으로 실행 중입니다: **http://localhost:8080**

## 📝 주요 변경사항

### 제거된 기능
- ❌ JWT 토큰 인증
- ❌ Firebase 인증
- ❌ Spring Security 복잡한 설정
- ❌ 모든 인증/인가 로직

### 간소화된 기능
- ✅ 이메일 기반 단순 로그인
- ✅ 모든 API 엔드포인트 자유 접근
- ✅ userId 파라미터로 사용자 식별

## 🔧 API 사용법

### 1. 로그인 (회원가입 자동)
```bash
curl -X POST "http://localhost:8080/api/auth/login?email=test@example.com"
```

응답:
```json
{
  "message": "로그인 성공",
  "userId": 1,
  "email": "test@example.com"
}
```

### 2. 시나리오 목록 조회
```bash
curl http://localhost:8080/api/scenarios
```

### 3. 시나리오 생성 (userId 파라미터 사용)
```bash
curl -X POST "http://localhost:8080/api/scenarios?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "source": "COMPANY",
    "title": "테스트 시나리오",
    "description": "간단한 테스트",
    "repoUrl": "https://github.com/test/repo",
    "dueAt": "2026-12-31T23:59:59"
  }'
```

### 4. 제출 생성
```bash
curl -X POST "http://localhost:8080/api/scenarios/1/submissions" \
  -H "Content-Type: application/json" \
  -d '{
    "repoUrl": "https://github.com/myrepo/solution",
    "description": "My solution"
  }'
```

### 5. 댓글 생성 (userId 파라미터 사용)
```bash
curl -X POST "http://localhost:8080/api/submissions/1/comments?userId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "body": "Great work!"
  }'
```

## 🎯 시연 시나리오

1. **로그인**: 이메일로 간단 로그인
2. **시나리오 조회**: 기존 시나리오 목록 확인
3. **제출 생성**: 특정 시나리오에 솔루션 제출
4. **댓글 작성**: 제출물에 피드백 남기기

## 📌 주의사항

- 모든 API가 인증 없이 접근 가능
- userId 파라미터로 사용자 구분 (기본값: 1)
- 실제 배포 시에는 보안 기능 재추가 필요
- 데모/MVP 목적으로만 사용

## 🛠️ 애플리케이션 제어

### 중지
```bash
# 실행 중인 프로세스 찾기
ps aux | grep bootRun

# 프로세스 종료
kill <PID>
```

### 재시작
```bash
cd /Users/jiu/dev/careerquest
./gradlew bootRun
```

## 🌐 Swagger UI

**예정**: http://localhost:8080/api-test
(Swagger UI 경로 확인 필요)

## ✅ 작동 확인 완료

- ✅ 빌드 성공
- ✅ 애플리케이션 실행 중
- ✅ 데이터베이스 연결 완료
- ✅ API 엔드포인트 응답 정상
- ✅ 로그인 기능 동작
- ✅ 시나리오 조회 가능

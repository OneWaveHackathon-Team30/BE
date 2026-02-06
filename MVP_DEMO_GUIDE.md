# CareerQuest MVP Demo Guide

## 🚀 실행 완료!

애플리케이션이 성공적으로 실행 중입니다: **http://localhost:8080**

## 📝 주요 변경사항

### 제거된 기능
- ❌ JWT 토큰 인증
- ❌ Firebase 인증
- ❌ Spring Security 복잡한 설정
- ❌ 모든 인증/인가 로직

### 구현된 기능
- ✅ 이메일 + 비밀번호 자체 로그인
- ✅ 간단한 회원가입 (비밀번호 평문 저장 - MVP용)
- ✅ 모든 API 엔드포인트 자유 접근
- ✅ userId 파라미터로 사용자 식별

## 🔧 API 사용법

### 1. 회원가입
```bash
curl -X POST "http://localhost:8080/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@test.com",
    "password": "1234",
    "nickname": "테스터"
  }'
```

응답:
```json
{
  "message": "회원가입 성공",
  "userId": 2,
  "email": "test@test.com"
}
```

### 2. 로그인
```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@test.com",
    "password": "1234"
  }'
```

응답 (성공):
```json
{
  "message": "로그인 성공",
  "userId": 2,
  "email": "test@test.com"
}
```

응답 (실패):
```json
{
  "message": "이메일 또는 비밀번호가 올바르지 않습니다"
}
```

### 3. 시나리오 목록 조회
```bash
curl http://localhost:8080/api/scenarios
```

### 4. 시나리오 생성 (userId 파라미터 사용)
```bash
curl -X POST "http://localhost:8080/api/scenarios?userId=2" \
  -H "Content-Type: application/json" \
  -d '{
    "source": "COMPANY",
    "title": "테스트 시나리오",
    "description": "간단한 테스트",
    "repoUrl": "https://github.com/test/repo",
    "dueAt": "2026-12-31T23:59:59"
  }'
```

### 5. 제출 생성
```bash
curl -X POST "http://localhost:8080/api/scenarios/1/submissions" \
  -H "Content-Type: application/json" \
  -d '{
    "repoUrl": "https://github.com/myrepo/solution",
    "description": "My solution"
  }'
```

### 6. 댓글 생성 (userId 파라미터 사용)
```bash
curl -X POST "http://localhost:8080/api/submissions/1/comments?userId=2" \
  -H "Content-Type: application/json" \
  -d '{
    "body": "Great work!"
  }'
```

## 🎯 시연 시나리오

1. **회원가입**: 이메일, 비밀번호, 닉네임으로 회원가입
2. **로그인**: 이메일, 비밀번호로 로그인하여 userId 획득
3. **시나리오 조회**: 기존 시나리오 목록 확인
4. **제출 생성**: 특정 시나리오에 솔루션 제출
5. **댓글 작성**: 제출물에 피드백 남기기

## 📌 주의사항

- 비밀번호가 평문으로 저장됨 (해싱 없음 - MVP 간소화)
- 모든 API가 인증 없이 접근 가능
- userId 파라미터로 사용자 구분
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

## ✅ 작동 확인 완료

- ✅ 빌드 성공
- ✅ 애플리케이션 실행 중
- ✅ 데이터베이스 연결 완료
- ✅ API 엔드포인트 응답 정상
- ✅ 회원가입 기능 동작
- ✅ 로그인 기능 동작 (비밀번호 검증 포함)
- ✅ 시나리오 조회 가능


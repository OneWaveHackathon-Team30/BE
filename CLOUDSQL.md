# Cloud SQL 설정 가이드

## 프로파일 설명

### local (기본값)
- H2 인메모리 데이터베이스 사용
- 로컬 개발 환경용
- 애플리케이션 재시작 시 데이터 초기화

### dev
- Cloud SQL (MySQL) 연결
- 개발 서버용
- `ddl-auto: update` - 자동 스키마 업데이트

### prod
- Cloud SQL (MySQL) 연결
- 프로덕션 환경용
- `ddl-auto: validate` - 스키마 검증만 수행
- 연결 풀 최적화 설정

## Cloud SQL 설정 방법

### 1. Cloud SQL 인스턴스 정보 확인
```bash
gcloud sql instances list
```

### 2. 환경 변수 설정
`.env` 파일 생성 (`.env.example` 참고):
```bash
CLOUD_SQL_INSTANCE=프로젝트ID:리전:인스턴스명
DB_NAME=데이터베이스명
DB_USER=사용자명
DB_PASSWORD=비밀번호
```

### 3. 프로파일 지정하여 실행

#### 로컬 개발 (H2)
```bash
./gradlew bootRun
# 또는
./gradlew bootRun --args='--spring.profiles.active=local'
```

#### Cloud SQL 개발 환경
```bash
export CLOUD_SQL_INSTANCE=your-project:region:instance
export DB_NAME=careerquest
export DB_USER=your-user
export DB_PASSWORD=your-password

./gradlew bootRun --args='--spring.profiles.active=dev'
```

#### Cloud SQL 프로덕션 환경
```bash
export CLOUD_SQL_INSTANCE=your-project:region:instance
export DB_NAME=careerquest
export DB_USER=your-user
export DB_PASSWORD=your-password

./gradlew bootRun --args='--spring.profiles.active=prod'
```

### 4. IntelliJ에서 실행
1. Run/Debug Configurations 열기
2. Environment variables 설정:
   ```
   CLOUD_SQL_INSTANCE=your-project:region:instance;
   DB_NAME=careerquest;
   DB_USER=your-user;
   DB_PASSWORD=your-password
   ```
3. VM options에 프로파일 추가:
   ```
   -Dspring.profiles.active=dev
   ```

### 5. Docker 배포 시
```dockerfile
ENV SPRING_PROFILES_ACTIVE=prod
ENV CLOUD_SQL_INSTANCE=your-project:region:instance
ENV DB_NAME=careerquest
ENV DB_USER=your-user
ENV DB_PASSWORD=your-password
```

## Cloud SQL 권한 설정

애플리케이션이 Cloud SQL에 연결하려면 다음 권한이 필요합니다:

### 로컬에서 테스트
```bash
# Application Default Credentials 설정
gcloud auth application-default login
```

### GCP 환경 (Cloud Run, GKE 등)
서비스 계정에 다음 역할 부여:
- `Cloud SQL Client` (roles/cloudsql.client)

```bash
gcloud projects add-iam-policy-binding PROJECT_ID \
  --member="serviceAccount:SERVICE_ACCOUNT_EMAIL" \
  --role="roles/cloudsql.client"
```

## 데이터베이스 초기화

### MySQL 사용자 및 데이터베이스 생성
```sql
CREATE DATABASE IF NOT EXISTS careerquest;
CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON careerquest.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
```

## 트러블슈팅

### Connection timeout
- Cloud SQL 인스턴스가 실행 중인지 확인
- 인스턴스 연결 이름이 올바른지 확인
- 서비스 계정 권한 확인

### Authentication failed
- DB_USER, DB_PASSWORD 환경 변수 확인
- MySQL 사용자 권한 확인

### Socket factory error
- Cloud SQL MySQL Socket Factory 의존성 확인
- Java 버전 호환성 확인 (Java 8+)

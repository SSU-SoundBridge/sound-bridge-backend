# Sound Bridge Backend

## 기술 스택
- Java 17
- Spring Boot 3.5
- PostgreSQL 16
- Gradle
- JPA (Hibernate)
- Lombok
- Docker

## 프로젝트 구조
```
project-root/
├── Dockerfile                  # Spring Boot 앱 빌드/실행용
├── docker-compose.yml          # 전체 서비스 오케스트레이션
└── docker/
    ├── app/
    │   └── entrypoint.sh       # (필요시) 앱 컨테이너용 스크립트
    └── db/
        └── init.sql           # (필요시) DB 초기화 SQL
src/main/java/com/ssu/soundbridge/
 ├── SoundBridgeApplication.java   # 메인 엔트리포인트
 ├── config/                      # 설정 관련 (JPA 등)
 ├── controller/                  # REST API 컨트롤러
 ├── domain/                      # 엔티티 클래스
 ├── dto/                         # 데이터 전송 객체
 ├── exception/                   # 전역 예외 처리
 ├── repository/                  # JPA 레포지토리
 ├── service/                     # 비즈니스 로직
 └── util/                        # 유틸리티 클래스
```

## Branch & PR Convention
- `main` 브랜치에서 `feature/브랜치명` 생성 후 작업
- PR 제목: 작업에 대한 요약 (디데일할 필요 없음)
- 커밋 메시지: Conventional Commits 스타일(`feat`, `fix`, `chore` 등)

### 브랜치 네이밍 예시
- `feature/user-signup`
- `fix/login-error`
- `hotfix/db-connection`
- `chore/gradle-update`

### 커밋 메시지 예시
```
feat: implement user signup API
fix: correct login validation logic
chore: update gradle dependencies
```

## Java/Spring Code Convention
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) + [Spring 공식 컨벤션](https://github.com/spring-projects/spring-framework/wiki/Code-Style) 준수
- 클래스/인터페이스: `UpperCamelCase`
- 변수/메서드: `lowerCamelCase`
- 상수: `UPPER_SNAKE_CASE`
- 들여쓰기: 4 spaces
- 중괄호 `{}`는 같은 줄에 시작
- 파일당 public 클래스는 하나
- 패키지명은 모두 소문자, 단어 구분은 점(`.`)
- import 순서: java → javax → 외부 라이브러리 → 프로젝트 내부
- 어노테이션은 클래스/메서드/필드 바로 위에 붙임
- Javadoc(/** ... */) 주석 권장

### Spring 추가 권장사항
- Controller, Service, Repository 등 계층별 역할 명확히 분리
- @Autowired 대신 생성자 주입(@RequiredArgsConstructor + final)
- DTO와 Entity 분리
- 예외는 커스텀 Exception + GlobalExceptionHandler로 처리

---

## 실행 방법
### 1. 데이터베이스 띄우기 (docker compose)
```bash
docker compose up -d
```
- PostgreSQL 16 컨테이너가 5432 포트로 실행됩니다.
- DB 정보는 application.yml과 동일하게 세팅되어 있습니다.

### 2. Spring Boot 앱 실행
```bash
brew install gradle
gradle wrapper
./gradlew bootRun
```

### 3. 추후 Spring Boot 도커라이징
- docker-compose.yml의 app 서비스 주석 해제 및 Dockerfile 추가 예정
- DB 연결은 서비스 네트워크로 자동 연결

## 주요 폴더 설명
- **controller/**: API 엔드포인트를 담당합니다.
- **service/**: 비즈니스 로직을 처리합니다.
- **repository/**: 데이터베이스 접근을 담당합니다.
- **domain/**: 데이터베이스 테이블과 매핑되는 엔티티 클래스입니다.
- **dto/**: 데이터 전송 객체로, 엔티티와 분리하여 사용합니다.
- **exception/**: 전역 예외 처리 및 커스텀 예외를 관리합니다.
- **config/**: JPA 등 공통 설정을 관리합니다.
- **util/**: 공통 유틸리티 클래스를 관리합니다.

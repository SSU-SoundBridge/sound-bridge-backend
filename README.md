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

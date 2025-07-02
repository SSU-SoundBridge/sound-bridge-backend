# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# 빌드 결과물 jar 파일을 복사 (실제 jar 이름에 맞게 수정 필요)
COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"] 
# syntax=docker/dockerfile:1.7

# ── 1) 빌드 (Gradle + JDK 21) ───────────────────────────
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# 의존성 캐시 레이어 분리: gradle 래퍼 + 빌드 스크립트 먼저 복사
COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
RUN chmod +x gradlew && ./gradlew --no-daemon dependencies > /dev/null 2>&1 || true

# 소스 복사 후 bootJar
COPY src ./src
RUN ./gradlew --no-daemon bootJar -x test \
    && cp build/libs/*.jar app.jar

# ── 2) 런타임 (JRE only) ────────────────────────────────
FROM eclipse-temurin:21-jre AS runner
WORKDIR /app

# 비루트 사용자
RUN groupadd --system --gid 1001 spring \
    && useradd --system --uid 1001 --gid spring spring

COPY --from=builder --chown=spring:spring /app/app.jar /app/app.jar

USER spring
EXPOSE 4000

# 시크릿(JWT_SECRET, DB_*, GOOGLE_CLIENT_*)은 런타임 env 로 주입.
# 이미지에 ENV 로 굳히지 않음.
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

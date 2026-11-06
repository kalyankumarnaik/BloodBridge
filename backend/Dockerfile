# Stage 1: Build JAR with Maven & OpenJDK 25
FROM maven:3.9.9-eclipse-temurin-21 AS builder
# Note: Eclipse Temurin provides stable builder; we use OpenJDK 25 early-access/runtime if available, or Eclipse Temurin
WORKDIR /build

COPY pom.xml ./
RUN mvn dependency:go-offline -B || true

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Production JRE Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S bloodbridge && adduser -S bloodbridge -G bloodbridge
USER bloodbridge:bloodbridge

COPY --from=builder /build/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]

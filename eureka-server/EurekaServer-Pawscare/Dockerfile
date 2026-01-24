# ===========================
# Stage 1: Build
# ===========================
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY mvnw .
COPY .mvn/ .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src src
RUN ./mvnw clean package -DskipTests

# ===========================
# Stage 2: Runtime
# ===========================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=builder /app/target/app.jar app.jar

EXPOSE 8080

# Healthcheck for AWS
HEALTHCHECK CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# JVM container-aware
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-XX:+UseContainerSupport","-jar","app.jar"]
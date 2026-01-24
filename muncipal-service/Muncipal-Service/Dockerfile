# ===========================
# Step 1: Build the Java app
# ===========================
FROM eclipse-temurin:17-jdk-alpine AS builder

# Container ke andar kaam karne ka folder
WORKDIR /app

# Maven wrapper aur project file copy karo
COPY mvnw .
COPY .mvn/ .mvn
COPY pom.xml .

# Maven wrapper ko executable banao
RUN chmod +x mvnw

# Dependencies download karo (cache ke liye)
RUN ./mvnw dependency:go-offline -B

# Source code copy karo
COPY src src

# Build the app aur tests skip karo (fast build)
RUN ./mvnw clean package -DskipTests

# ===========================
# Step 2: Runtime (lightweight)
# ===========================
FROM eclipse-temurin:17-jre-alpine

# Kaam ka folder set karo
WORKDIR /app

# Security: non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Builder stage se jar copy karo
COPY --from=builder /app/target/*.jar app.jar

# Spring Boot default port
EXPOSE 8080

# AWS friendly healthcheck
HEALTHCHECK CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# JVM container friendly settings
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-XX:+UseContainerSupport", "-jar", "app.jar"]

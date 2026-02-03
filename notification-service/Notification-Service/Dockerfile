# ===========================
# Stage 1: Build
# ===========================
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Maven wrapper aur configuration copy karein
COPY mvnw .
COPY .mvn/ .mvn
COPY pom.xml .

# Permissions set karein aur dependencies download karein
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Source code copy karke build karein
COPY src src
RUN ./mvnw clean package -DskipTests

# ===========================
# Stage 2: Runtime
# ===========================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Security ke liye non-root user banayein
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# FIX: Wildcard (*) use kiya hai taaki EurekaServer-Pawscare-0.0.1-SNAPSHOT.jar mil jaye
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

# Optimized JVM settings for EC2 (Free Tier)
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-XX:+UseContainerSupport","-jar","app.jar"]

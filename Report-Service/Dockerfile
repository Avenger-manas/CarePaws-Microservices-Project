# ===========================
# Stage 1: Build
# ===========================
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY mvnw .
COPY .mvn/ .mvn
COPY pom.xml .

RUN chmod +x mvnw
# Dependency download ke liye memory limit
RUN MAVEN_OPTS="-Xmx512m" ./mvnw dependency:go-offline -B

COPY src src
# MODIFIED: Compilation ke liye memory limit set ki hai taaki EC2 crash na ho
RUN MAVEN_OPTS="-Xmx512m" ./mvnw clean package -DskipTests

# ===========================
# Stage 2: Runtime
# ===========================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Wildcard fix jo aapne pehle kiya tha
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-XX:+UseContainerSupport","-jar","app.jar"]

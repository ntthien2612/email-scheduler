# ----- Stage 1: Build JAR -----
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml và source code
COPY pom.xml .
COPY src ./src

# Build ứng dụng, skip tests
RUN mvn clean package -DskipTests

# ----- Stage 2: Run ứng dụng -----
FROM openjdk:17-slim
WORKDIR /app

# Copy JAR từ stage build
COPY --from=builder /app/target/email-scheduler-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Chạy ứng dụng với giới hạn bộ nhớ
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]

ENV TZ=Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
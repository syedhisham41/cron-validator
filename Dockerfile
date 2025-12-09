FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM gcr.io/distroless/java21

WORKDIR /cron-validator
COPY --from=builder /app/target/cron-validator.jar app.jar
EXPOSE 8080
CMD ["app.jar"]

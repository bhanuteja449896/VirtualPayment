# Use an official Maven image with OpenJDK 21 as the base image
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Ensure the credentials file is included in the JAR (src/main/resources is bundled by default)
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
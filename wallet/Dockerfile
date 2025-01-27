# Use Maven to build the application
FROM maven:3.9.4-eclipse-temurin-21 as builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml file into the working directory
COPY wallet/pom.xml ./

# Copy the src directory into the working directory
COPY wallet/src ./src

# Run Maven to build the project
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:21-jdk-slim

# Set the working directory in the runtime image
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

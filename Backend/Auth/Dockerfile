# Use Java 21
FROM openjdk:21-jdk

# Set working directory in container
WORKDIR /app

# Copy JAR into container
COPY target/*.jar /app/auth-service.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app/auth-service.jar"]
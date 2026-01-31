# Use a lightweight JDK 21 image as the base
FROM eclipse-temurin:21-jre-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your host's target folder into the container
# Use the wildcard *.jar or replace with your exact filename (e.g., demo-0.0.1-SNAPSHOT.jar)
COPY target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

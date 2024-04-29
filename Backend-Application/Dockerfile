# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from your target folder to the Docker image
COPY target/Backend-Application-0.0.1-SNAPSHOT.jar Backend-Application-0.0.1-SNAPSHOT.jar

# Command to run the application
CMD ["java", "-jar", "/app/Backend-Application-0.0.1-SNAPSHOT.jar"]

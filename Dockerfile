# Use lightweight Java 17 image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy all project files
COPY . .

# Give permission to mvnw
RUN chmod +x mvnw

# Build the project (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Expose port (Render uses dynamic PORT)
EXPOSE 8080

# Run the generated jar file
CMD ["sh", "-c", "java -jar target/*.jar"]
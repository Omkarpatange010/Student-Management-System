# Use Java 17
FROM eclipse-temurin:17-jdk

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build project
RUN mvn clean package -DskipTests

# Expose port
EXPOSE 8080

# Run app
CMD ["sh", "-c", "java -jar target/*.jar"]
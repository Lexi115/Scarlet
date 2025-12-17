# Start from JDK 21 image.
FROM eclipse-temurin:21-alpine

# Setup dependencies.
WORKDIR /app/
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copy source code.
COPY src/ src/

# Run application.
CMD ["./mvnw", "spring-boot:run"]

# Stage 1: build
FROM maven:3.9.8-amazoncorretto-21 AS build

# Set working directory and copy files
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build source code with Maven
RUN mvn clean package -DskipTests

# Stage 2: create runtime image
FROM amazoncorretto:21.0.4

# Set working directory and copy the JAR file
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ARG SPRING_PROFILES_ACTIVE=prod
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

# Optional: Expose application port for documentation
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-Duser.timezone=Asia/Ho_Chi_Minh", "-jar", "app.jar"]

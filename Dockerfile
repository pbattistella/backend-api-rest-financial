#Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY src /home/backend/src
COPY pom.xml /home/backend
RUN mvn -f /home/backend/pom.xml clean package -DskipTests

#Package Stage
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /home/backend/target/*.jar /usr/local/lib/backend.jar
CMD ["java", "-jar", "/usr/local/lib/backend.jar"]
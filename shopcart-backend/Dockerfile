# Build stage
FROM maven:3.6.3-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -Dmaven.test.skip clean test package

# Package stage
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /home/app/target/*.jar shopcart-backend.jar
EXPOSE 8080
CMD ["java", "-jar", "shopcart-backend.jar"]

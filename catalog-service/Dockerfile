FROM openjdk:11-jre-slim

WORKDIR /app
COPY ./target/example-service-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "catalog-service-0.0.1-SNAPSHOT.jar"]

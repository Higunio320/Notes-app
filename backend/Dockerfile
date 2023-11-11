FROM maven:3.9.0-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .

COPY src/ src/

RUN mvn -f pom.xml clean package -DskipTests -X

EXPOSE 8080

ENTRYPOINT ["mvn", "clean", "spring-boot:run"]
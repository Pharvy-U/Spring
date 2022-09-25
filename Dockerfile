# syntax=docker/dockerfile:1

FROM eclipse-temurin:11.0.12_7-jdk

RUN mkdir -p /home/app
WORKDIR /home/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]

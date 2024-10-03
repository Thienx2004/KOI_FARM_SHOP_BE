FROM maven:3-openjdk-17 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests


# Run stage

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY src ./src

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]

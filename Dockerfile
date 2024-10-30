FROM maven:3-openjdk-17 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests


# Run stage

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/be.jar /app/be.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","be.jar"]

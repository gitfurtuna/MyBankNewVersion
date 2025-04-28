FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY authservice/pom.xml .
COPY authservice/src ./src
RUN mvn clean package

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/authservice_my_bank-0.0.1-SNAPSHOT.jar appAuth.jar
ENTRYPOINT ["java", "-jar", "appAuth.jar"]
EXPOSE 8080


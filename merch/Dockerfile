FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY merch/pom.xml .
COPY merch/src ./src
RUN mvn clean package

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/merch-0.0.1-SNAPSHOT.jar appMerch.jar
ENTRYPOINT ["java", "-jar", "appMerch.jar"]
EXPOSE 8081
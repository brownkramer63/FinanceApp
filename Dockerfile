FROM maven:3.9.9-eclipse-temurin-11 AS builder

#  Create a directory /app in the Container
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:11-jre
COPY --from=builder /app/target/sparkle-accounting.*jar /sparkle-accounting.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","sparkle-accounting.jar"]

FROM maven:3-jdk-11-slim as builder
COPY src src
COPY pom.xml pom.xml
RUN mvn install

FROM adoptopenjdk/openjdk11:alpine-jre as release
COPY --from=builder target/eureka-server-0.0.1.jar app.jar
EXPOSE 8761
CMD java -jar app.jar


FROM 885887105084.dkr.ecr.eu-west-1.amazonaws.com/maven:3-jdk-11-slim as builder
COPY src src
COPY pom.xml pom.xml
RUN mvn install

FROM 885887105084.dkr.ecr.eu-west-1.amazonaws.com/openjdk11:alpine-jre as release
COPY --from=builder target/spring-eureka-auth-0.0.1.jar app.jar
EXPOSE 8761
CMD java -jar app.jar


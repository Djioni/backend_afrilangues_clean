FROM maven:3.8.3-openjdk-17 as builder

WORKDIR /appAfrilangues

COPY .  afrilanguesApi

RUN mvn --file ./afrilanguesApi clean package -DskipTests


FROM openjdk:17-alpine

COPY --from=builder /appAfrilangues/afrilanguesApi/target/*.jar /appApi.jar

CMD ["java", "-jar", "/appApi.jar"]


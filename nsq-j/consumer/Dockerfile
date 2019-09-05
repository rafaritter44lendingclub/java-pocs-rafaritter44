FROM openjdk:8 AS builder
COPY . /usr/local/src/consumer/
WORKDIR /usr/local/src/consumer/
RUN ./gradlew clean build

FROM openjdk:8
WORKDIR /usr/local/bin/
COPY --from=builder /usr/local/src/consumer/build/libs/consumer.jar .
ENTRYPOINT ["java", "-jar", "consumer.jar"]
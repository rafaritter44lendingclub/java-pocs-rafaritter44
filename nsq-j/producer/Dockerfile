FROM openjdk:8 AS builder
COPY . /usr/local/src/producer/
WORKDIR /usr/local/src/producer/
RUN ./gradlew clean build

FROM openjdk:8
WORKDIR /usr/local/bin/
COPY --from=builder /usr/local/src/producer/build/libs/producer.jar .
ENTRYPOINT ["java", "-jar", "producer.jar"]
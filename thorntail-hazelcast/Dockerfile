FROM openjdk:8 AS builder
RUN apt update && apt install maven -y
COPY . /usr/local/src/thorntail-hazelcast/
WORKDIR /usr/local/src/thorntail-hazelcast/
RUN mvn package

FROM openjdk:8
WORKDIR /usr/local/bin/
COPY --from=builder /usr/local/src/thorntail-hazelcast/target/thorntail-hazelcast-thorntail.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "thorntail-hazelcast-thorntail.jar"]
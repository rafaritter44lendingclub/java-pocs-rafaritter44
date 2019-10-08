FROM openjdk:8 AS builder
RUN apt update && apt install maven -y
COPY . /usr/local/src/thorntail-ignite/
WORKDIR /usr/local/src/thorntail-ignite/
RUN mvn package

FROM openjdk:8
WORKDIR /usr/local/bin/
COPY --from=builder /usr/local/src/thorntail-ignite/target/thorntail-ignite-thorntail.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "thorntail-ignite-thorntail.jar"]
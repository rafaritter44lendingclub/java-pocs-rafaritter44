FROM openjdk:8
WORKDIR /opt/
ENV KAFKA_VERSION 2.3.0
ENV SCALA_VERSION 2.12
RUN wget "http://ftp.unicamp.br/pub/apache/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz"
RUN tar -xzf kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
COPY startup.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/startup.sh
ENTRYPOINT ["startup.sh"]
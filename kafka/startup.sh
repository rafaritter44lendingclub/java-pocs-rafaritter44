#!/bin/bash
cd /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION}
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &
bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic hello_topic --create --partitions 3 --replication-factor 1
tail -f logs/server.log
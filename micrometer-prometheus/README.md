# Micrometer + Prometheus + Grafana

Build and run app:
```
$ ./gradlew clean build
$ java -jar build/libs/micrometer-prometheus.jar
```

Install and run Prometheus:
```
$ wget https://github.com/prometheus/prometheus/releases/download/v2.14.0/prometheus-2.14.0.linux-amd64.tar.gz
$ tar -xzf prometheus-2.14.0.linux-amd64.tar.gz
$ rm prometheus-2.14.0.linux-amd64.tar.gz
$ prometheus-2.14.0.linux-amd64/prometheus --config.file=prometheus.yml
```

Install and run Grafana:
```
$ wget https://dl.grafana.com/oss/release/grafana-6.4.4.linux-amd64.tar.gz 
$ tar -zxvf grafana-6.4.4.linux-amd64.tar.gz
$ rm grafana-6.4.4.linux-amd64.tar.gz
$ cd grafana-6.4.4/bin/
$ ./grafana-server
```

Access Grafana to create dashboards:
```
$ xdg-open 'http://localhost:3000'
```

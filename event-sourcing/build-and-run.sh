#!/bin/bash
./gradlew clean uberJar
docker-compose build
docker-compose up

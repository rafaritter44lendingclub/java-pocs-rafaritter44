# Netty

## Running:
- `$ ./gradlew clean build` and `$ java -jar build/libs/netty.jar`
- or `$ ./gradlew run`

## Using:
- `$ curl -v -X POST -d '{"content": "Hello", "sender": "Ritter"}' http://localhost:8080/api/message`
- `$ curl -v http://localhost:8080/api/message`

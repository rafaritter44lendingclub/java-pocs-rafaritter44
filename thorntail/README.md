# Sample Thorntail Application

In order to run the application, it is necessary to:
1. Make sure you have Postgres installed
2. Create a Postgres database named "enterprise"
    1. Run `$ sudo -u postgres psql`
    2. Run `$ CREATE DATABASE enterprise;`
3. Run either `$ mvn thorntail:run` or `$ mvn package && java -jar ./target/thorntail-thorntail.jar`

The application should be listening on port 8080, and you should be able to call the following endpoints:

- GET /jaxrs-cdi-jpa/api/homework-assignment
- GET /jaxrs-cdi-jpa/api/homework-assignment/{id}
- DELETE /jaxrs-cdi-jpa/api/homework-assignment/{id}
- POST /jaxrs-cdi-jpa/api/homework-assignment

Sample request body for POST:
```json
{
  "name": "",
  "description": "",
  "estimatedDeadlineInDays": 1
}
```

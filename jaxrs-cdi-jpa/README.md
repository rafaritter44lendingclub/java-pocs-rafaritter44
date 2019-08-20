# Sample JAX-RS + CDI + JPA Application

In order to run the application, it is necessary to:
1. Download Tomcat from https://tomcat.apache.org/download-70.cgi
2. Unzip it
3. Build the application .war with `$ ./gradlew clean build`
4. Move the generated .war to {tomcat}/webapps/
5. Make sure you have Postgres installed
6. Create a Postgres database named "enterprise"
    1. Run `$ sudo -u postgres psql`
    2. Run `$ CREATE DATABASE enterprise;`
7. Run this script: {tomcat}/bin/startup.sh

After Tomcat deploys the application, it should be listening on port 8080, and you should be able to call the following endpoints:

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

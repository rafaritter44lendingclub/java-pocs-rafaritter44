# Sample Thorntail + Liquibase Application

In order to run the application, it is necessary to:
1. Make sure you have Postgres installed
2. Create a Postgres database named "thorntail_liquibase"
    1. Run `$ sudo -u postgres psql`
    2. Run `$ CREATE DATABASE thorntail_liquibase;`
3. Run `$ mvn liquibase:update thorntail:run`

The application should be listening on port 8080, and you should be able to call the following endpoints:

- GET /api/homework-assignment
- GET /api/homework-assignment/{id}
- DELETE /api/homework-assignment/{id}
- POST /api/homework-assignment

Sample request body for POST:
```json
{
  "name": "",
  "description": "",
  "estimatedDeadlineInDays": 1
}
```

# Sample Thorntail + Ignite Application

Run the following command:

`$ docker-compose up --scale thorntail=2`

You will have 2 instances of the application running, one listening on port 8080, and the other one on 8079. They will be sharing persistence and cache, and you should be able to call the following endpoints:

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

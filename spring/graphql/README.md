# Spring + GraphQL

## Building
`$ ./gradlew clean build`

## Running
`$ java -jar build/libs/graphql.jar`

## Accessing
- Browse to http://localhost:8080/graphiql

## Using

- Creating author:
```
mutation {
  newAuthor(firstName: "Eric", lastName: "Evans") {
    id
    firstName
    lastName
  }
}
```

- Creating book:
```
mutation {
  newBook(name: "Domain-Driven Design", pageCount: 529, authorId: "7d23fe9e-f4c4-4d12-908a-bed094f3894b") {
    id
    name
    pageCount
  }
}
```

- Getting book by ID:
```
{
  bookById(id: "cf78eeba-2a4f-436d-a57b-73cb64539584") {
    id
    name
    pageCount
    author {
      id
      firstName
      lastName
    }
  }
}
```

- Getting books by author:
```
{
  booksByAuthorId(authorId: "bbdc6e7f-5ee6-4408-af68-063698c9b50d") {
    id
    name
    pageCount
  }
}
```

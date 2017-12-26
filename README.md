# Spring-boot and GraphQL demo

A simple Spring Boot app with GraphQL implementation using [GraphQL-SPQR](https://github.com/leangen/graphql-spqr).

> GraphQL SPQR - Makes it easy to add GraphQL API support to any Java Project. 

**How to use**: 

The app has GraphiQL embedded in it, all you need to do is run the app in your favourite IDE.

**Query Example**:

1. A simple Query to get all the author names and their ids will look like this. 
```
{
    allAuthors {
        id
        name
    }
}
```
2. A more complex Query to get all the author names and the posts they wrote, will look like this. 
```
{
    allAuthors {        
        name
        posts {
            content
            comments {
                content
            }
        }
    }
}
```
**Mutation Example**
1. Create Author mutation will look something like this:
```
mutation {
  createAuthor(name: "William Shakespeare") {
    name
    id
  }
}
```

Visit [How to GraphQL](https://www.howtographql.com/) to learn more about GraphQL 

> **Happy Coding!**


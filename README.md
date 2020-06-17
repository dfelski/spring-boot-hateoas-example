# Spring Boot HATEOAS example

Small example how [HATEOAS](https://en.wikipedia.org/wiki/HATEOAS) can be implemented using [Spring Boot](https://spring.io/projects/spring-boot) and [Spring HATEOAS](https://spring.io/projects/spring-hateoas).

## Peanut Bowl Scenario
Let's say we are able to create bowls and fill them with peanuts. Anf of course we can snack them ;) 

### Create a bowl
Let's put an empty bowl on the table.

```
curl -X POST http://localhost:8080/bowls
```
The response always contains the current bowl resource, you can find e.g. the bowl id for further steps  

### Fill a bowl with peanuts
No one needs empty bowls, let's fill it with peanuts.

```
curl -X PUT http://localhost:8080/bowls/3105ec9b-9f05-49d0-9918-d4313fd29140/fillPeanuts -H "Content-Type: application/json" -d '{"peanuts":10}'
```

### Let's check our peanut level
```
curl http://localhost:8080/bowls/3105ec9b-9f05-49d0-9918-d4313fd29140
```

### Take out peanuts
We can only snack peanuts, if the bowl is not empty.

```
curl -X POST http://localhost:8080/bowls/3105ec9b-9f05-49d0-9918-d4313fd29140/snackPeanuts -H "Content-Type: application/json" -d '{"peanuts":10}'
```

### Remove a bowl
We can only remove bowls, if they are empty.

```
curl -X DELETE http://localhost:8080/bowls/3105ec9b-9f05-49d0-9918-d4313fd29140
```

## HAL representation
The responses should all have some links based on the [HAL](http://stateless.co/hal_specification.html) specification:

```
curl http://localhost:8080/bowls/3105ec9b-9f05-49d0-9918-d4313fd29140

{
    "id":"16a9f13b-6dd1-4586-9193-d2f22286bf8a",
    "peanuts":0,
    "_links": {
        "self": {
            "href":"http://localhost:8080/bowls/16a9f13b-6dd1-4586-9193-d2f22286bf8a"
        },
        "fill": {
            "href":"http://localhost:8080/bowls/16a9f13b-6dd1-4586-9193-d2f22286bf8a/fillPeanuts"
        },
        "delete":{
            "href":"http://localhost:8080/bowls/16a9f13b-6dd1-4586-9193-d2f22286bf8a"
        }
    }
}
```
The links existence depends on the business logic described above. 

# Spring Boot Application - Cache Implementation with Redis

## Intro

This is my first caching-related personal project. In this project, Redis is used as in-memory NoSQL caching method with PostgreSQL as the main database. There
is only one table in the main database which is the products table. Implementation of caching in Spring Boot is quite easy, but you need to pay attention
for Redis configuration especially when its deployed as containerized application by configuring the `bind 0.0.0.0` (all interface) and set the protected-mode
to `no`. This project also utilized Liquibase as Database Change Management (migration things) as a learning material for myself.

## How to run

```
mvn clean package
docker-compose up -d
```

Spring boot application running exposed on port 8080 on your localhost

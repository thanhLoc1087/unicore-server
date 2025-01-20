# Unicore

## Overview
Unicore is a Classroom Management System, designed to help institutions manage key data such as students, lecturers, subjects, and classrooms.

## Features
* Student, Lecturer Management: Add, update, delete, and retrieve student, lecturer information.
* Subject Management: Assign lecturers to subjects, manage subject data.
* Classroom Management: Track classroom information, including capacity and room numbers.
* Bulk Import: Users can import students, lecturers, subjects, and classrooms via Excel files.
* Notification Service: Informs users about the success/failure of bulk imports.
* Asynchronous Processing (optional): Supports background job queues for handling large imports.

## Tech Stack
### Backend
* Java + Spring Boot: Framework for building RESTful APIs and handling business logic.
* Spring MVC: For exposing REST endpoints.
* Spring Data JPA: ORM layer for database access and transactions.
* Spring Batch (optional): For handling background processing and large imports.
* Spring Webflux: Asynchronous processing for non-blocking imports.

### Database

* MySQL: Relational database for managing structured data, including students, lecturers, subjects, and classrooms.
* MongoDB: Non relational database to store notices, and support real-time messaging.
* ACID-compliant for reliable transactions.
* Full-text search for querying names and other text fields.

### Message Queue (Optional)
* Kafka: Used for handling background processing and asynchronous tasks, especially for large imports.

### Build Tools
* Maven: Dependency management and build automation for the Spring Boot project.

### Other Tools
* Docker: For containerizing the application and managing development environments.

## System Architecture
* API Gateway: Exposes REST endpoints for handling operations like importing data, managing students/lecturers/subjects/classrooms.
* Service Layer: Contains business logic such as parsing Excel files, validating data, and managing database operations.
* Database: MySQL and MongoDB.
* Background Job Queue: Manages long-running operations like large imports using asynchronous processing or message brokers.
* Notification Service: Sends feedback to users regarding the status of their import operations (success or failure).

## Prerequisites
* Java SDK 21
* A MySQL server

## Start application
`mvn spring-boot:run`

## Build application
`mvn clean package`

## Docker guideline
### Build docker image
`docker build -t <account>/unicore-server:0.1.0 .`
### Push docker image to Docker Hub
`docker image push <account>/unicore-server:0.9.0`
### Create network:
`sudo docker network create unicore-network`

`sudo docker run -d --name mysql-unicore -p 3306:3306 --hostname mysql --network unicore-network -e MYSQL_ROOT_PASSWORD=0843300042 -e MYSQL_DATABASE=init_db mysql:8.0.39-debian`
`sudo docker run -d --name mysql-unicore -p 3306:3306 --hostname mysql -e MYSQL_ROOT_PASSWORD=0843300042 -e MYSQL_DATABASE=init_db mysql:8.0.39-debian`

`sudo docker run -d --name mongodb-unicore -p 27017:27017  --hostname mongodb --network unicore-network -e MONGODB_ROOT_PASSWORD=0843300042 -e GLIBC_TUNABLES=glibc.pthread.rseq=0 bitnami/mongodb:latest`

`sudo docker run -d --name eureka-server-unicore --hostname eureka-server --network unicore-network -p 8761:8761 thanhloc1087/unicore-eureka-server:0.1.0`

`sudo docker run -d --name api-gateway-unicore -p 8080:8888 -e ORG_SERVICE_URL=3.104.75.6 -e PROFILE_SERVICE_URL=13.239.233.211 -e CLASSROOM_SERVICE_URL=3.107.48.180 -e CLASSEVENT_SERVICE_URL=3.106.167.64 -e POST_SERVICE_URL=13.211.252.145 thanhloc1087/unicore-api-gateway:0.6.1`


`sudo docker run -d --name organization-service-unicore --network unicore-network -p 8081:8081 -e MYSQL_USER=root -e MYSQL_PASSWORD=0843300042 -e ORG_DB=organizationdb -e API_GATEWAY_URL=3.107.202.61:8080 thanhloc1087/unicore-organization-service:0.8.2`
`sudo docker run -d --name organization-service-unicore -p 8081:8081 -e MYSQL_URL=54.206.100.239:3306 -e MYSQL_USER=root -e MYSQL_PASSWORD=0843300042 -e ORG_DB=organizationdb -e API_GATEWAY_URL=3.107.202.61:8080 thanhloc1087/unicore-organization-service:0.8.7`

`sudo docker run -d --name profile-service-unicore --network unicore-network -p 8082:8082 -e MYSQL_USER=root -e MYSQL_PASSWORD=0843300042 -e PROFILE_DB=profiledb -e API_GATEWAY_URL=3.107.202.61:8080 thanhloc1087/unicore-profile-service:0.8.1`
`sudo docker run -d --name profile-service-unicore -p 8082:8082 -e MYSQL_URL=54.206.100.239:3306 -e MYSQL_USER=root -e MYSQL_PASSWORD=0843300042 -e PROFILE_DB=profiledb -e API_GATEWAY_URL=3.107.202.61:8080 thanhloc1087/unicore-profile-service:0.8.9`

`sudo docker run -d --name classroom-service-unicore --network unicore-network -p 8083:8083 -e MONGODB_PASSWORD=0843300042 -e API_GATEWAY_URL=3.107.202.61:8080 thanhloc1087/unicore-classroom-service:0.6.1`

`sudo docker run -d --name classevent-service-unicore --network unicore-network -p 8084:8084 -e MONGODB_PASSWORD=0843300042 -e API_GATEWAY_URL=3.107.202.61:8080 thanhloc1087/unicore-classevent-service:0.6.0`

`sudo docker run -d --name post-service-unicore --network unicore-network -p 8086:8086 -e MONGODB_PASSWORD=0843300042 -e API_GATEWAY_URL=3.107.202.61:8080 thanhloc1087/unicore-post-service:0.5.0`


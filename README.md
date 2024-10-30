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
`docker network create devteria-network`
### Start MySQL in devteria-network
`docker run --network devteria-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.36-debian`
### Run your application in devteria-network
`docker run --name identity-service --network devteria-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql:3306/identity_service unicore-server:0.9.0`
# Classroom Management System

## Overview
The Classroom Management System is designed to help institutions manage key data such as students, lecturers, subjects, and classrooms. It supports bulk data import via Excel files, efficiently creating up to 100 records in a single request. This system features a relational database (PostgreSQL) for structured data storage, a Spring Boot-based backend for processing, and optional asynchronous processing for handling large imports.

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
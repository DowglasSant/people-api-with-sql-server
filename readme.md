# Person API

A simple CRUD API built in **Java** with **Spring Boot** to explore **SQL Server** integration and **Hexagonal Architecture** principles. This project was developed for study purposes and demonstrates the management of persons and their addresses.

---

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [API Endpoints](#api-endpoints)
- [Entity Structure](#entity-structure)
- [DTOs](#dtos)
- [Running the Application](#running-the-application)

---

## Overview

This API allows basic CRUD operations for `Person` entities, including their associated `Address`. It is implemented following **Hexagonal Architecture**, separating domain, application, and infrastructure layers to promote maintainability and testability.

Key features:
- Create, read, update, and delete persons.
- Manage one-to-one relationship between `Person` and `Address`.
- Data persistence using **SQL Server**.
- RESTful API endpoints with JSON input/output.

---

## Architecture

The project follows **Hexagonal Architecture**:

- **Domain Layer** (`domain.model` & `domain.port`): Contains core entities (`Person`, `Address`) and repository ports (`PersonRepositoryPort`).
- **Application Layer** (`application.service`, `application.dto`, `application.mapper`): Handles business logic, DTOs, and mapping between entities and DTOs.
- **Infrastructure Layer** (`infrastructure.repository`, `infrastructure.controller`): Implements the repository adapters, Spring Data repositories, and REST controllers.

The design allows easy replacement of infrastructure (e.g., database) without affecting domain logic.

---

## Technologies

- Java 21
- Spring Boot
- Spring Data JPA
- SQL Server
- Keycloak
- Swagger
- Spring Boot Actuator
- Lombok
- Jakarta Validation
- Maven
- JUnit 5
- Mockito

---

## API Endpoints

Base URL: `/api/persons`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | `/api/persons` | Get all persons |
| GET    | `/api/persons/{id}` | Get a person by ID |
| GET    | `/api/persons/cpf/{cpfNumber}` | Get a person by CPF number |
| GET    | `/api/persons/city/{city}` | Get persons by city |
| POST   | `/api/persons` | Create a new person |
| PUT    | `/api/persons/{id}` | Update an existing person |
| DELETE | `/api/persons/{id}` | Delete a person by ID |

**Example JSON for creating a person:**
```json
{
  "cpfNumber": "12345678901",
  "email": "john.doe@example.com",
  "birthDate": "1990-01-01",
  "phone": "123456789",
  "name": "John Doe",
  "address": {
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "neighborhood": "Manhattan",
    "zipcode": "10001",
    "houseNumber": "123"
  }
}
```

## Entity Structure

### Person
- `id` (Integer, PK)
- `cpfNumber` (String, unique)
- `email` (String, unique)
- `birthDate` (LocalDate)
- `phone` (String)
- `name` (String)
- `address` (One-to-One relationship with Address)

### Address
- `id` (Integer, PK)
- `person_id` (FK to Person)
- `city` (String)
- `state` (String)
- `country` (String)
- `neighborhood` (String)
- `zipcode` (String)
- `houseNumber` (String)

---

## DTOs

- **PersonRequestDTO**: Used for incoming requests to create or update a person.  
- **PersonResponseDTO**: Used for returning person data in responses.  
- **AddressDTO**: Embedded in Person DTOs to represent address data.  

---

## Running the Application

1. Configure SQL Server connection in `application.properties`:
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=pessoa
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## Build and Run

### Build the project with Maven:
```bash
mvn clean install
```

### Run the application:
```bash
mvn spring-boot:run
```

### Access the API at:
```bash
http://localhost:8080/api/people
```

## Tests

This project includes **unit tests** for both the **service** and **controller** layers, implemented using **JUnit 5** and **Mockito**.

- **Service tests**: Validate business logic by mocking repository interactions.
- **Controller tests**: Validate REST endpoints using **MockMvc**, mocking service and mapper layers to test request handling and responses.

### Run tests

Execute all tests with Maven:

```bash
mvn test
```
### All tests are located under:

```bash
src/test/java/com/santana/dowglas/sql_server_application
```

This ensures that both the business logic and API endpoints work as expected before deploying the application.
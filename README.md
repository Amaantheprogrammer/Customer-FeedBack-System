# Customer Feedback System

A secure and scalable RESTful backend application built using **Spring Boot**, designed to manage customer feedback submissions with **JWT-based authentication**, **role-based authorization**, and **CRUD operations**. The system enables users to submit feedback while allowing administrators to manage and monitor all feedback records.

---

## Features

### Authentication & Authorization

* User registration and login
* JWT-based authentication
* BCrypt password encryption
* Custom `UserDetailsService` integration
* Role-Based Access Control (RBAC)
* Roles:

  * USER
  * ADMIN

### User Management

* Register new users
* Retrieve user details
* Update user profile information
* Username-based user lookup

### Feedback Management

* Create feedback
* Retrieve feedback by ID
* Retrieve all feedback with pagination
* Retrieve feedback sorted by date
* Filter feedback by date
* Update feedback
* Delete feedback

### Security Features

* Spring Security integration
* JWT token generation and validation
* Endpoint-level authorization
* Protected APIs based on user roles

### Exception Handling

* Global exception handling
* Resource not found handling
* Password mismatch validation
* Access denied handling
* Structured error responses

---

## Tech Stack

### Backend

* Java 17
* Spring Boot 4
* Spring Security
* Spring Data JPA
* Hibernate
* JWT (JSON Web Token)

### Database

* MySQL

### Utilities

* Lombok
* ModelMapper

### Build Tool

* Maven

---

## Project Structure

```text
src/main/java
│
├── auth
│   ├── controller
│   ├── dto
│   ├── jwt
│   ├── service
│   └── custom
│
├── user
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
│
├── feedback
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
│
├── config
├── exception
└── common_dto
```

---

## Database Models

### User

| Field    | Type         |
| -------- | ------------ |
| id       | Long         |
| username | String       |
| email    | String       |
| password | String       |
| role     | USER / ADMIN |

### Feedback

| Field    | Type      |
| -------- | --------- |
| id       | Long      |
| feedback | String    |
| date     | LocalDate |

---

## API Endpoints

### Authentication

#### Register User

```http
POST /auth/sign-up
```

Request:

```json
{
  "username": "amaan",
  "email": "amaan@example.com",
  "password": "password123",
  "confirmPassword": "password123"
}
```

#### Login

```http
POST /auth/sign-in
```

Request:

```json
{
  "username": "amaan",
  "password": "password123"
}
```

Response:

```json
{
  "token": "jwt-token"
}
```

---

### User APIs

#### Get User By ID

```http
GET /users/{id}
```

Role Required:

```text
ADMIN
```

#### Get User By Username

```http
GET /users/username/{username}
```

Role Required:

```text
ADMIN
```

#### Update User

```http
PATCH /users/update-user/{id}
```

Role Required:

```text
USER, ADMIN
```

---

### Feedback APIs

#### Create Feedback

```http
POST /feedbacks
```

Role Required:

```text
USER, ADMIN
```

#### Get Feedback By ID

```http
GET /feedbacks/{id}
```

Role Required:

```text
ADMIN
```

#### Get All Feedbacks

```http
GET /feedbacks
```

Role Required:

```text
ADMIN
```

Supports pagination:

```http
GET /feedbacks?page=0&size=10
```

#### Get Feedbacks Sorted By Date

```http
GET /feedbacks/date
```

Role Required:

```text
ADMIN
```

#### Get Feedbacks By Specific Date

```http
GET /feedbacks/date-desc?date=2026-09-01
```

Role Required:

```text
ADMIN
```

#### Update Feedback

```http
PUT /feedbacks/{id}
```

Role Required:

```text
ADMIN
```

#### Delete Feedback

```http
DELETE /feedbacks/{id}
```

Role Required:

```text
ADMIN
```

---

## Security Rules

| Endpoint             | Access      |
| -------------------- | ----------- |
| /auth/**             | Public      |
| GET /users/**        | ADMIN       |
| PATCH /users/**      | USER, ADMIN |
| GET /feedbacks/**    | ADMIN       |
| POST /feedbacks/**   | USER, ADMIN |
| PUT /feedbacks/**    | ADMIN       |
| DELETE /feedbacks/** | ADMIN       |

---

## Running the Application

### Clone Repository

```bash
git clone <repository-url>
cd CustomerFeedBackSystem
```

### Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/customer_feedback_db
spring.datasource.username=root
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

Application starts at:

```text
http://localhost:5000
```
---

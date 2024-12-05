# Book Catalog Management System

A robust microservices-based book catalog application built with modern Java technologies and best practices.

## Project Overview

This system demonstrates implementation of a scalable book catalog management solution using:
- Java 8 with Spring Boot 2.7.18
- Microservices architecture
- RESTful API design principles
- Docker containerization
- Comprehensive testing

### Architecture

The application consists of two microservices:
1. **Management Service**: Core backend API handling book data and business logic
2. **Web Service**: Client interface providing user interaction

## Service Specifications

### Management Service (Backend)

**Core Technologies**

| Technology       | Implementation                |
|------------------|-------------------------------|
| Language         | Java 8                        |
| Framework        | Spring Boot 2.7.18            |
| Data Access      | Spring Data JPA               |
| Database         | H2 In-Memory Database         |
| Build Tool       | Maven                         |
| Containerization | Docker                        |
| Testing          | JUnit 5, Mockito, Spring Test |

**Key Features**
- Full CRUD operations for book management
- RESTful API with standardized responses
- Robust error handling and validation
- Comprehensive test coverage
- In-memory data persistence

**API Endpoints**

| Method | Endpoint             | Description       | Query Parameters           |
|--------|----------------------|-------------------|----------------------------|
| GET    | `/api/v1/books`      | List all books    | `page`, `perPage`, `query` |
| POST   | `/api/v1/books`      | Create a new book | -                          |
| PUT    | `/api/v1/books/{id}` | Update a book     | -                          |
| DELETE | `/api/v1/books/{id}` | Delete a book     | -                          |

[View Complete API Documentation in Postman](https://grey-crater-653575.postman.co/workspace/PayU-Assessment-~420c5687-1418-4b5c-a6e8-839cb91d3c25/collection/21506559-d419515d-c31c-4323-863f-dfad02988d93?action=share&creator=21506559)

[Swagger Link for API enpoints](http://localhost:8000/swagger-ui/index.html)

\* You have to be running the service to access the swagger page

### Web Service (Frontend)

**Core Technologies**

| Technology       | Implementation         |
|------------------|------------------------|
| Language         | Java 8                 |
| Framework        | Spring Boot 2.7.18     |
| View Engine      | JSP (with TailwindCSS) |
| Build Tool       | Maven                  |
| Containerization | Docker                 |

## Development & Testing

### Testing Strategy

The application implements a comprehensive testing approach:
- Unit Tests: Individual component validation
- Integration Tests: End-to-end workflow verification
- Test Driven Development (TDD) methodology

To run tests:
```bash
./mvnw test
```

## Setup & Deployment

### Prerequisites
- Docker Desktop installed and running
- Port 8000 and 8010 available on host machine

### Backend Service Deployment
```bash
cd book-management-service
docker build -t book-management-service .
docker run -p 8000:8000 book-management-service
```

### Frontend Service Deployment
```bash
cd book-management-web
docker build -t book-management-web .
docker run -p 8010:8010 -e API_BASE_URL=http://host.docker.internal:8000/api/v1 book-web
```

## Domain Model

### Book Entity
- Title (required)
- ISBN (required, unique)
- Author
- Publication Date
- Price
- Book Type (HARD_COVER, SOFT_COVER, E_BOOK, AUDIO_BOOK)

## Future Enhancements

1. **Feature Additions**
    - Advanced search and filtering
    - User authentication and authorization
    - Book categories and tags
    - Rating and review system
    - Inventory Management
    - Patron Management
    - Lending Operations

2. **Technical Improvements**
    - Metrics and monitoring
    - Implementation of shared library for common code

## Contributing

Please refer to our contributing guidelines for information on how to propose changes or improvements to this project.

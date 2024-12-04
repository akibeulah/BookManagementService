# Book Catalog Management System

Technical Assessment Submission

## Project Overview
A microservices-based book catalog application demonstrating proficiency in:
- Java 8
- Spring Boot 2.x
- Microservice architecture
- RESTful API design
- Docker containerization

### System Architecture
Two-tier Spring Boot application:
1. **Management Service**: Backend API
2. **Web Service**: Client Interface

## Backend Web Service
**Technologies**

| Section          | Technologies       |
|------------------|--------------------|
| Language         | Java 8             |
| Framework        | Spring Boot 2.7.18 |
| Template Engine  | JSP                |
| Library Bundler  | Maven              |
| Containerization | Docker             |



## Backend Management Service
**Technologies**

| Section          | Technologies          |
|------------------|-----------------------|
| Language         | Java 8                |
| Framework        | Spring Boot 2.7.18    |
| JDBC             | Spring Data JPA       |
| Database         | H2 In-Memory Database |
| Library Bundler  | Maven                 |
| Containerization | Docker                |

### Key Features
- CRUD operations for book catalog
- RESTful API endpoints
- In-memory database persistence
- Comprehensive error handling

### API Endpoints
- `GET /books`: Retrieve all books
- `POST /books`: Create new book
- `PUT /books/{id}`: Update existing book
- `DELETE /books/{id}`: Remove book

[Here is a Postman link for the endpoints](https://grey-crater-653575.postman.co/workspace/PayU-Assessment-~420c5687-1418-4b5c-a6e8-839cb91d3c25/collection/21506559-d419515d-c31c-4323-863f-dfad02988d93?action=share&creator=21506559)

### Entity Relationships
- **Books**: Catalog information

___
## Development Approach

### Testing Strategy
- Unit Testing: Component-level validation
- Integration Testing: End-to-end workflow
- Test Driven Development
- Ensuring robust service interactions

## Local Setup & Execution

### Prerequisites
- Java 8 JDK
- Maven 3.x
- Docker (optional)

### Docker Deployment
```bash
docker-compose up --build
```

## Project Highlights
- Microservices architecture
- RESTful API design
- In-memory database integration
- Dockerized deployment
- Comprehensive test coverage

## Potential Improvements
- Advanced search functionality
- User authentication
- Performance optimization
- Enhanced error reporting
- Implementation of shared library
# ChâTop - Rental Application

##  Description

ChâTop is a back-end application developed with Java and Spring Boot that connects tenants and property owners. This application provides a secure REST API for managing real estate rental listings.

##  Project Objectives

- Develop a REST API with Spring Boot
- Implement authentication and authorization with Spring Security
- Manage data persistence with Spring Data JPA
- Document the API with Swagger/OpenAPI
- Apply Java development best practices

##  Technologies Used

- **Java** 17 or higher
- **Spring Boot** 3.x
- **Spring Security** - JWT Authentication
- **Spring Data JPA** - Data Persistence
- **MySQL** - Database
- **Maven** - Dependency Management
- **Swagger/OpenAPI** - API Documentation
- **Lombok** - Boilerplate Code Reduction

##  Project Structure
```
src/
  main/
    java/
      com/chatop/
        configuration/     # SwaggerOpenAPI class Configuration
        controller/        # REST Controllers
        model/             # JPA Entities
        repository/        # JPA Repositories
        service/           # Business Services
        dto/               # Data Transfer Objects
        security/          # Security Configuration
    resources/
      application.properties
  test/
    java/                  # Unit and Integration Tests
```
    
##  Installation and Setup

### Prerequisites

- JDK 17 or higher
- Maven 3.6+
- MySQL 8.0+ or PostgreSQL 12+
- An IDE (IntelliJ IDEA, Eclipse, VS Code)

### Database Configuration

1. Create a database:
```sql
CREATE DATABASE Rental_db;
```

2. Configure the `application.properties` file:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Rental_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
server.servlet.context-path=/api

# JWT Configuration
jwt.secret=your_secret_key
jwt.expiration=3600000  
```
### Installation

1. Clone the repository:
```bash
git clone https://github.com/hafidaAssendal/Developpez-le-back-end-en-utilisant-Java-et-Spring.git
cd Developpez-le-back-end-en-utilisant-Java-et-Spring
```

2. Install dependencies:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will be accessible at :
```
http://localhost:8080/api
```

##  API Documentation

Once the application is running, Swagger documentation is accessible at:
```
http://localhost:8080/swagger-ui.html
```

### Main Endpoints

#### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and obtain JWT token
- `GET /api/auth/me` - Get current user information

#### Rentals
- `GET /api/rentals` - Get all rental listings
- `GET /api/rentals/{id}` - Get rental details
- `POST /api/rentals` - Create a new rental (authentication required)
- `PUT /api/rentals/{id}` - Update a rental (authentication required)

#### Messages
- `POST /api/messages` - Send a message (authentication required)

#### Users
- `GET /api/user/{id}` - Get user information

##  Security

The application uses JWT (JSON Web Token) for authentication. To access protected endpoints:

1. Obtain a token via `/api/auth/login`
2. Add the token in your request headers:
```
Authorization: Bearer your_jwt_token
```
##  Author
Hafida Assendal

GitHub: @hafidaAssendal

**Note**: This project was developed as part of the "Full-Stack Developer - Java and Angular" path at OpenClassrooms.

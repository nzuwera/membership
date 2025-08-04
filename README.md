[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=membership&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=membership) [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=membership&metric=bugs)](https://sonarcloud.io/summary/new_code?id=membership)  [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=membership&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=membership) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=membership&metric=coverage)](https://sonarcloud.io/summary/new_code?id=membership) [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=membership&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=membership) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=membership&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=membership) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=membership&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=membership)  [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=membership&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=membership)  [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=membership&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=membership)

# Membership Management System

A Spring Boot application for managing membership data. This application allows you to create and manage member records with different membership plan types (UNLIMITED or LIMITED) with a default expiry period of 30 days.

## Features

- Create new members with personal information (first name, last name, email, date of birth)
- Assign membership plan types (UNLIMITED or LIMITED)
- REST API for programmatic access
- Web interface for user interaction

## Technology Stack

- Java with Spring Boot
- Spring MVC for web interface
- Spring REST for API endpoints
- JPA/Hibernate for database access
- PostgreSQL database
- Flyway for database migrations
- Thymeleaf for server-side templating

## API Endpoints

- `POST /member/create-member` - Create a new member

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Database (configured in application.properties)

### Running the Application

1. Clone the repository
2. Configure your database connection in `application.properties`
3. Run the application using Maven:
   ```
   mvn spring-boot:run
   ```
4. Access the web interface at `http://localhost:8080/`

### API Usage Example

Create a new member:
```
POST /member/create-member
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "dateOfBirth": "1990-01-01",
  "plan": "UNLIMITED"
}
```

## Future Enhancements

The application is designed to be extended with additional functionality:
- Update existing members
- Find members by email
- List all members
- Delete members
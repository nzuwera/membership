# Spring Boot Style Guide for Membership Application

This document outlines the coding standards, architecture patterns, and best practices for the Membership Application.

## Technology Stack

### Frontend
- **Thymeleaf** As the view layer, Thymeleaf is a modern server-side Java template engine for both web and standalone environments.
- **Bootstrap 5.3.x** for styling.
- **Vanilla JS** for client-side scripting.

### Backend
- **Spring Boot 5.3.x** as the application framework.
- **Spring Data JPA** for persistence.
- **Spring Security** for authentication and authorization. 
- **Spring Actuator** for monitoring and management.
- **Spring Web** for MVC and REST.
- **Spring Validation** for validation.
- **PostgreSQL 16.x** as the database.
- **Flyway** for database migrations.

### Testing
- **JUnit 5** for unit testing.
- **Testcontainers** for integration testing.
- **DO NOT use Mockito** - implement tests using Testcontainers.
- **DO NOT use Mocks** - prefer real dependencies or test doubles.

### Excluded Technologies
- **DO NOT use Lombok** - write standard Java code instead.
- **DO NOT use MapStruct** - implement mappers manually.
- **DO NOT use H2** - use PostgreSQL instead.

## Architecture Patterns

### Package Structure
```
com.nzuwera.membership/
├── config/         # Application configuration.
├── controllers/    # REST controllers and Web MVC controllers.
├── dto/            # Data transfer objects.
├── exceptions/     # Custom exceptions.
├── mappers/        # Manual DTO-Entity mappers
├── models/         # Domain entities.
├── repositories/   # Data access layer.
└── services/       # Business logic.
```

### Layered Architecture
The application follows a strict layered architecture with the following layers:

- **Controller Layer** - handles incoming requests and delegates to the appropriate service.
- **Service Layer** - Contains business logic, coordinates operations across the application, and delegates to the repository layer.
- **Repository Layer** - Manages data access and persistence.
- **Model Layer** - represents domain entities.

The flow of control should always follow the following pattern: Controller -> Service -> Repository -> Database. Each layer should be independent of the others.

## Idiomatic Usage

### Naming Conventions
* Use camelCase for method and variable names.
* Use PascalCase for class names.
* Use UPPER_SNAKE_CASE for constants.
* Use `this` for instance variables and `static` for class variables.
* Use `get` and `set` for getters and setters.
* Use `is` for boolean getters.
* Use `add` and `remove` for collections.
* Use `has` for boolean checks.

### Controllers
- Use `@Controller` for web pages with Thymeleaf.
- Use `@RestController` for API endpoints.
- Follow RESTful naming conventions for endpoints:
  - Use nouns instead of verbs (e.g., `/users` instead of `/user/list`).
  - Use plural nouns for collections (e.g., `/users` instead of `/user`).
  - Use HTTP methods appropriately:
    - GET for retrieving data
    - POST for creating data
    - PUT for updating data
    - DELETE for deleting data
  - Use hierarchies for nested resources (e.g., `/users/{id}/orders` instead of `/users/{id}/order`).
- Returns appropriate HTTP status codes for each endpoint:
  - 200 OK for successful requests GET, PUT, PATCH
  - 201 Created for successful creation requests POST
  - 204 No Content for successful deletion requests DELETE
  - 400 Bad Request for invalid requests
  - 401 Unauthorized for unauthorized requests
  - 403 Forbidden for requests that the user is not authorized to perform
  - 404 Not Found for requests that do not match any resources
  - 409 Conflict for requests that result in a conflict (e.g., duplicate keys)
  - 500 Internal Server Error for unexpected errors
- Validate input using Bean Validation annotations (`@Valid`)
- Implement global error handling using `@ControllerAdvice` and `@ExceptionHandler`
- Use proper error handling messaging with clear descriptions and error codes.

### Services 
- Implement business logic in service classes.
- Use interface for services when appropriate.
- Handle transactions at the service layer with `@Transactional`.

### Repositories
- Use Spring Data JPA repositories.
- Define custom queries with `@Query` annotations.
- Keep repository methods focused on data access and not business logic.
- Use `Pageable` and `Slice` for pagination where appropriate.

### DTOs
- Use DTOs for API requests and responses.
- Implement manual mappers for DTOs to entities.
- Keep DTOs immutable when possible.

## Pitfalls to Avoid

### Security and Compliance
- Do not commit secrets or environment-specific configurations to source control; rely on externalized configuration and environment variables.
- Do not log sensitive data (passwords, tokens, PII); implement log redaction policies and review logs for leakage.
- Do not expose stack traces or internal details in error responses in production.
- Do not disable CSRF protection globally; ensure forms and API clients send CSRF tokens where relevant.
- Do not rely on default session settings; configure session timeout, cookie flags (Secure, HttpOnly, SameSite), and rotation on login.
- Do not store sensitive information in session or client-side storage; minimize stored data and encrypt where necessary.
- Do not skip transport security; enforce HTTPS everywhere and HSTS in production.
- Do not ignore data protection obligations; document data retention, consent, and right-to-erasure flows.

### API Design and Contracts
- Do not return inconsistent error payloads; define a consistent error schema with codes, messages, and trace/correlation IDs.
- Do not ignore content negotiation; validate and respond with appropriate media types (e.g., 415/406 when needed).
- Do not misuse HTTP semantics; keep POST non-idempotent, PUT idempotent, and PATCH for partial updates.
- Do not omit pagination, sorting, and filtering conventions for list endpoints; define defaults and maximum limits.
- Do not skip validation error detail; include field-level messages and constraints violated.
- Do not ignore cache controls; use ETags/If-None-Match or Last-Modified for cacheable GET endpoints when applicable.
- Do not mix view models and API DTOs; keep contracts stable and versioned when breaking changes are needed.

### Persistence, JPA, and Migrations
- Do not modify existing migration files; add new forward-only migrations and track changes with clear descriptions.
- Do not deploy migrations manually; run them automatically as part of application startup or the deployment pipeline.
- Do not leave mismatches between entity mappings and DDL; keep constraints, indexes, and nullability aligned.
- Do not ignore N+1 issues; use proper fetching strategies, projections, or entity graphs where appropriate.
- Do not rely on lazy-loading outside transaction scope; design service methods to load required data within boundaries.
- Do not overuse cascading operations; be explicit about cascade and orphan removal to avoid unintended deletes/updates.
- Do not ignore concurrency; use optimistic locking (version fields) where concurrent updates are possible.
- Do not overlook timezone handling; store timestamps in UTC and convert at the edges.

### Transaction and Concurrency
- Do not manage transactions in controllers or repositories; define transaction boundaries in the service layer.
- Do not create stateful service beans; services should be stateless and thread-safe.
- Do not ignore isolation requirements; choose appropriate isolation levels for operations prone to phantom reads or write skew.
- Do not block on long-running operations in request threads; offload to async or messaging where needed.

### Configuration and Deployment
- Do not hardcode environment-specific values; use profiles (application-{profile}.properties) and environment overrides.
- Do not expose actuator endpoints broadly; protect sensitive endpoints and segment management interfaces.
- Do not run containers with default or privileged users; use least-privilege users and set resource limits.
- Do not pin to floating “latest” versions; use explicit, reproducible versions for base images and dependencies.
- Do not skip graceful shutdown; ensure proper SIGTERM handling and time to complete in-flight requests.
- Do not forget readiness/liveness checks; provide meaningful health indicators for dependencies.

### Static Assets and Templating
- Do not hardcode URLs in templates; use link helpers consistently to avoid broken paths across environments.
- Do not mix unescaped user content into templates; prefer escaped output and sanitize any rich text as needed.
- Do not reference non-existent static resources; keep asset pipeline and resource versioning (cache-busting) consistent.
- Do not mix CDN and local resources inconsistently; document the policy and fallbacks.

### Testing Strategy
- Do not share a single database instance across tests without isolation; use disposable environments and clean state.
- Do not couple tests to execution order; make each test independent and self-contained.
- Do not only test the happy path; include validation failures, error handling, and concurrency scenarios.
- Do not overlook rollback scenarios; verify transactional behavior on exceptions.
- Do not skip seeding reference data in tests via migrations; keep test schema aligned with production.

### Observability and Operations
- Do not log at incorrect levels; keep INFO concise, use DEBUG/TRACE sparingly, and alert on ERROR appropriately.
- Do not omit structured logging; prefer key-value or JSON logs to enable search and aggregation.
- Do not forget correlation/trace IDs; propagate them across threads and external calls.
- Do not deploy without metrics; expose application, JVM, and database metrics and set actionable alerts.

### Performance and Resilience
- Do not leave connection pools at defaults; right-size database and HTTP client pools for expected load.
- Do not ignore backpressure; use timeouts, retries with jitter, and circuit breakers where appropriate.
- Do not transfer oversized payloads blindly; consider compression, streaming, and pagination.
- Do not bypass caching opportunities; apply appropriate caching at query, method, or HTTP levels with clear TTLs.
- Do not block the main thread with CPU-heavy work; offload to worker pools with bounded queues.

### Documentation and Governance
- Do not leave architectural decisions implicit; record ADRs for significant changes.
- Do not skip dependency and vulnerability management; review and update regularly with automated scanning.
- Do not neglect code ownership and review; require reviews for changes to critical modules and schemas.

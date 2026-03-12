# Java Coding Guidelines (Hexagonal Architecture)

## 1. General Principles
- Follow SOLID, DRY, and KISS principles.
- Prefer immutability for value objects and DTOs.
- Use meaningful, descriptive names for classes, methods, and variables.
- Keep methods short and focused; one responsibility per method.

## 2. Hexagonal Architecture Conventions
- **Domain Module**: Contains business logic, entities, value objects, services, and ports (interfaces). No dependencies on application or infrastructure.
- **Application Module**: Exposes APIs, handles input validation, request mapping, and response formatting. Calls domain use cases via ports.
- **Infrastructure Module**: Implements ports (repositories, external services), handles persistence and integrations. Depends on domain.

## 3. Package Structure
- Organize by module: application/, domain/, infrastructure/
- Inside each module, use clear package names: com.belairbuvette.domain.entity, com.belairbuvette.application.controller, etc.

## 4. Dependency Management
- Domain must not depend on application or infrastructure.
- Application depends on domain and infrastructure.
- Infrastructure depends on domain.

## 5. Code Style
- Use spaces for indentation (2 or 4, consistent across project).
- Annotate classes and methods with Javadoc for public APIs.
- Use @Override, @Nullable, @NotNull annotations where appropriate.

## 6. Exception Handling
- Prefer custom exceptions for domain errors.
- Avoid catching generic Exception; handle specific cases.
- Propagate exceptions through ports, handle in application layer.

## 7. DTOs and Mapping
- Use DTOs for API boundaries.
- Map DTOs to domain objects in application layer.
- Avoid exposing domain entities directly in API responses.

## 8. Testing
- Write unit tests for domain logic.
- Use integration tests for application and infrastructure modules.
- Mock ports in application tests.

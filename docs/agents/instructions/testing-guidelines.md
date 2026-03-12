# Testing Guidelines

## 1. General Principles
- Test early, test often: aim for high coverage of business logic.
- Prefer fast, deterministic tests.
- Use descriptive names for test methods and classes.

## 2. Unit Testing
- Focus on domain logic (entities, value objects, services, use cases).
- Mock dependencies (ports, repositories, external services).
- Test one behavior per test method.
- Place unit tests in domain/src/test/java/com/...

## 3. Integration Testing
- Test interactions between modules (application, infrastructure).
- Use real implementations for adapters, mock only external systems.
- Cover API endpoints, persistence, and service integrations.
- Place integration tests in application/src/test/java/com/... and infrastructure/src/test/java/com/...

## 4. Test Structure & Naming
- Use Given-When-Then structure in test methods.
- Name test classes after the class under test (e.g., OrderServiceTest).
- Name test methods to describe behavior (e.g., shouldCreateOrderWhenValidInput).

## 5. Test Data & Fixtures
- Use builders or factory methods for test data.
- Avoid hardcoding values; prefer reusable fixtures.

## 6. Assertions & Validation
- Use expressive assertions (assertThat, assertEquals, etc.).
- Validate both positive and negative scenarios.

## 7. Test Coverage & Maintenance
- Aim for >80% coverage of domain logic.
- Review and update tests when refactoring or adding features.
- Remove obsolete or redundant tests.

## 8. Continuous Integration
- Run tests automatically on each commit and pull request.
- Fail builds if tests fail.

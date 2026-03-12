# Refactor Order API to Hexagonal Architecture

## Description
The Order REST API is currently tightly coupled to persistence and service layers, making maintenance and testing difficult. We need to refactor the Order API to follow Hexagonal Architecture principles:

- Extract business logic into the Domain module (entities, value objects, services, ports).
- Move REST controllers and DTOs to the Application module.
- Implement repository and external service adapters in the Infrastructure module.
- Ensure proper separation of concerns and dependency direction.

## Implementation Plan
1. Identify and extract business logic from Order API.
2. Create Domain entities, value objects, services, and ports.
3. Refactor REST controllers to delegate to Domain use cases via ports.
4. Move persistence and external integrations to Infrastructure.
5. Update and add unit/integration tests.

## Gherkin Test Scenarios

### Scenario: Successful order creation
Given a valid order request
When the API receives the request
Then the order is processed by the Domain logic
And the response contains the created order details

### Scenario: Order creation fails due to invalid input
Given an invalid order request
When the API receives the request
Then the Domain logic returns a validation error
And the response contains an error message

### Scenario: Order creation fails due to external service error
Given a valid order request
When the API receives the request
And the external service is unavailable
Then the Infrastructure adapter returns an error
And the response contains an error message

# Drink Order (Application)

## Context
Festival goers can place orders for drinks. The Application module exposes the REST API for drink ordering.

## Success Criteria
- API allows ordering alcoholic and non-alcoholic drinks.
- Token deduction logic is enforced per drink type.
- Order cannot exceed available drink tokens.

## Implementation Plan
- Define DTOs for drink order request/response.
- Implement REST controller endpoint.
- Integrate with Domain use case.
- Validate drink type and token balance.

## Gherkin Scenarios

### Scenario: Order non-alcoholic drink
Given a festival goer with 6 drink tokens
When they order a non-alcoholic drink
Then the API does not deduct any drink tokens

### Scenario: Order normal alcoholic drink
Given a festival goer with 6 drink tokens
When they order a normal alcoholic drink
Then the API deducts 1 drink token

### Scenario: Order premium alcoholic drink
Given a festival goer with 6 drink tokens
When they order a premium alcoholic drink
Then the API deducts 2 drink tokens

### Scenario: Insufficient tokens
Given a festival goer with 1 drink token
When they order a premium alcoholic drink
Then the API rejects the order due to insufficient tokens

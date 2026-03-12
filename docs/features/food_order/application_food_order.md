# Food Order (Application)

## Context
Festival goers can place orders for food items (snacks and meals). The Application module exposes the REST API for food ordering.

## Success Criteria
- API allows ordering snacks and meals.
- Token deduction logic is enforced per food type.
- Order cannot exceed available snack tokens.

## Implementation Plan
- Define DTOs for food order request/response.
- Implement REST controller endpoint.
- Integrate with Domain use case.
- Validate food type and token balance.

## Gherkin Scenarios

### Scenario: Order snack
Given a festival goer with 9 snack tokens
When they order a snack
Then the API deducts 1 snack token

### Scenario: Order meal
Given a festival goer with 9 snack tokens
When they order a meal
Then the API deducts 3 snack tokens

### Scenario: Insufficient tokens
Given a festival goer with 2 snack tokens
When they order a meal
Then the API rejects the order due to insufficient tokens

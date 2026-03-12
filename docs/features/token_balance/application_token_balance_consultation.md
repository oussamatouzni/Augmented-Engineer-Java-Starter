# Application - Token Balance Consultation

**Contexte**
Les festivaliers doivent pouvoir consulter leur solde de jetons boissons et snacks. L'API doit garantir l'exactitude du solde, empêcher les valeurs négatives et appliquer la logique d'allocation quotidienne.
**Critères d'acceptation**
Feature: Token balance consultation
	Scenario: Festival goer consults token balance
	Given a festival goer with 6 drink tokens and 9 snack tokens for today
	When they request their token balance
	Then the API returns 6 drink tokens and 9 snack tokens
	Scenario: Festival goer with zero tokens
		Given a festival goer with 0 drink tokens and 0 snack tokens
		When they request their token balance
		Then the API returns 0 drink tokens and 0 snack tokens
	Scenario: Negative balance prevention
		Given a festival goer with -1 drink tokens
	When they request their token balance
	Then the API returns 0 drink tokens and does not allow negative balances

**Notes**
- Le solde de jetons ne peut jamais être négatif.
- Les jetons non utilisés ne sont pas reportés au lendemain.
# Token Balance Consultation (Application)

## Context
Festival goers need to consult their remaining drink and snack token balances. The Application module exposes the REST API for this feature.

## Success Criteria
- API endpoint returns correct token balances for both types.
- Negative balances are prevented.
- Daily token allocation logic is enforced.

## Implementation Plan
- Define DTOs for token balance response.
- Implement REST controller endpoint.
- Integrate with Domain use case.
- Validate input and handle errors.

## Gherkin Scenarios

### Scenario: Festival goer consults token balance
Given a festival goer with 6 drink tokens and 9 snack tokens for today
When they request their token balance
Then the API returns 6 drink tokens and 9 snack tokens

### Scenario: Festival goer with zero tokens
Given a festival goer with 0 drink tokens and 0 snack tokens
When they request their token balance
Then the API returns 0 drink tokens and 0 snack tokens

### Scenario: Negative balance prevention
Given a festival goer with -1 drink tokens
When they request their token balance
Then the API returns 0 drink tokens and does not allow negative balances

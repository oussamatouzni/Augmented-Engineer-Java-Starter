# Multi-Item Order (Application)

## Context
Festival goers can order multiple items in a single order. The Application module exposes the REST API for multi-item ordering.

## Success Criteria

# Application - Multi-Item Order

**Contexte**
Les festivaliers peuvent commander plusieurs items dans une seule commande. L'API doit vérifier que le coût total ne dépasse pas le solde de jetons disponible pour chaque type.

**Critères d'acceptation**
Feature: Multi-item order
	Scenario: Order multiple items within balance
		Given a festival goer with 6 drink tokens and 9 snack tokens
		When they order 2 normal alcoholic drinks and 3 snacks
		Then the API deducts 2 drink tokens and 3 snack tokens
	Scenario: Order exceeds token balance
		Given a festival goer with 1 drink token and 2 snack tokens
		When they order 2 premium alcoholic drinks and 3 meals
		Then the API rejects the order due to insufficient tokens

**Notes**
- Les commandes multi-items doivent respecter le solde de chaque type de jeton.
- Validate total cost against token balances.

## Gherkin Scenarios

### Scenario: Order multiple items within balance
Given a festival goer with 6 drink tokens and 9 snack tokens
When they order 2 normal alcoholic drinks and 3 snacks
Then the API deducts 2 drink tokens and 3 snack tokens

### Scenario: Order exceeds token balance
Given a festival goer with 1 drink token and 2 snack tokens
When they order 2 premium alcoholic drinks and 3 meals
Then the API rejects the order due to insufficient tokens

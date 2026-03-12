# Application - Token Transfer

**Contexte**
Un festivalier peut transférer jusqu'à trois jetons de chaque type à un autre festivalier, avec confirmation du destinataire. L'API doit permettre la gestion du transfert et la validation du solde.

**Critères d'acceptation**
Feature: Token transfer
  Scenario: Transfer tokens within limit
    Given a festival goer with 3 drink tokens and 3 snack tokens
    When they transfer 2 drink tokens and 1 snack token to another festival goer
    Then the transfer is successful and the recipient confirms
  Scenario: Transfer exceeds limit
    Given a festival goer with 5 drink tokens
    When they attempt to transfer 4 drink tokens
    Then the API rejects the transfer
  Scenario: Negative balance prevention
    Given a festival goer with 1 snack token
    When they transfer 2 snack tokens
    Then the API rejects the transfer due to insufficient balance

**Notes**
- Le transfert doit être confirmé par le destinataire.
# Application - Group Token Pooling Order

**Contexte**
Les groupes de festivaliers peuvent mutualiser leurs jetons pour passer une commande de groupe. L'API doit permettre la création d'une commande de groupe, vérifier la suffisance des jetons, et gérer la contribution de chaque membre.

**Critères d'acceptation**
Feature: Group token pooling order
  Scenario: Group order with sufficient pooled tokens
    Given a group of 3 festival goers with a total of 10 drink tokens and 15 snack tokens
    When they place a group order costing 8 drink tokens and 12 snack tokens
    Then the order is accepted and tokens are deducted from each contributor
  Scenario: Group order with insufficient tokens
    Given a group of 2 festival goers with a total of 2 drink tokens
    When they place a group order costing 5 drink tokens
    Then the order is rejected due to insufficient pooled tokens
  Scenario: Individual contribution tracking
    Given a group order
    When each festival goer contributes a custom amount of tokens
    Then the API records each contribution and deducts accordingly

**Notes**
- La commande de groupe doit suivre les mêmes règles que les commandes individuelles.
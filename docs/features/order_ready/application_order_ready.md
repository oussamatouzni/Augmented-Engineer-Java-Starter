# Application - Order Ready

**Contexte**
Le barman peut marquer une commande comme prête lorsque tous les items sont préparés. L'API doit permettre la notification du festivalier pour le retrait de la commande.

**Critères d'acceptation**
Feature: Order ready
  Scenario: Mark order as ready
    Given a bartender with a prepared order
    When they mark the order as ready
    Then the festival goer is notified to pick up the order
  Scenario: Insufficient prepared items
    Given a bartender with an order missing items
    When they attempt to mark the order as ready
    Then the API rejects the action

**Notes**
- La commande ne peut être marquée comme prête que si tous les items sont préparés.
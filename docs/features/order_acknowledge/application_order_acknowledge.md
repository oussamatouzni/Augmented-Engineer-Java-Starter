# Application - Order Acknowledgement

**Contexte**
Le barman peut reconnaître une commande pour indiquer qu'elle est en préparation et fournir une estimation du temps de préparation. L'API doit permettre la reconnaissance de la commande et le calcul du temps estimé.

**Critères d'acceptation**
Feature: Order acknowledgement
  Scenario: Acknowledge order and provide estimate
    Given a bartender with a new order
    When they acknowledge the order
    Then the festival goer is notified and receives an estimated time of readiness
  Scenario: Estimate calculation for mixed items
    Given an order with drinks and meals
    When the bartender acknowledges the order
    Then the estimated time is calculated according to the rules

**Notes**
- Le temps de préparation doit suivre les règles définies dans FEATURES.md.
# Application - Order Change

**Contexte**
Un festivalier peut modifier sa commande tant qu'elle n'a pas été reconnue par le barman. L'API doit permettre d'ajouter ou retirer des items, vérifier le solde de jetons, et notifier le barman si la commande est déjà reconnue.

**Critères d'acceptation**
Feature: Order change
  Scenario: Change order before acknowledgement
    Given a festival goer with an unacknowledged order
    When they add or remove items
    Then the order is updated and token balance is checked
  Scenario: Change order after acknowledgement
    Given a festival goer with an acknowledged order
    When they request a change
    Then the bartender is notified and must approve or reject the change
  Scenario: Modified order exceeds token balance
    Given a festival goer with 2 snack tokens
    When they add items costing 5 snack tokens
    Then the change is rejected due to insufficient tokens

**Notes**
- Les modifications doivent être traçables et notifier le barman si nécessaire.
# Application - Order Cancellation

**Contexte**
Un festivalier peut annuler sa commande tant qu'elle n'a pas été reconnue par le barman. L'API doit permettre l'annulation, le remboursement des jetons, et la confirmation de l'annulation.

**Critères d'acceptation**
Feature: Order cancellation
  Scenario: Cancel order before acknowledgement
    Given a festival goer with an unacknowledged order
    When they cancel the order
    Then the tokens are refunded and a confirmation is sent
  Scenario: Cancel order after acknowledgement
    Given a festival goer with an acknowledged order
    When they request cancellation
    Then the bartender is notified and must approve or reject the cancellation

**Notes**
- Les jetons doivent être remboursés uniquement si la commande n'est pas reconnue.
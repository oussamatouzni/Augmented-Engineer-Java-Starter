# Application - Bartender Review Order Change

**Contexte**
Le barman doit pouvoir examiner et approuver ou rejeter les demandes de modification de commandes déjà reconnues. L'API doit permettre la gestion de ces demandes et notifier le festivalier du résultat.

**Critères d'acceptation**
Feature: Bartender review order change
  Scenario: Approve change with transferable items
    Given a bartender with a change request
    When at least one item can be transferred
    Then the change is approved and the festival goer is notified
  Scenario: Reject change with no transferable items
    Given a bartender with a change request
    When no items can be transferred
    Then the change is rejected and the festival goer is notified

**Notes**
- Les transferts d'items doivent être gérés selon les règles de FEATURES.md.
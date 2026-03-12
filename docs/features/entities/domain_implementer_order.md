# Implémenter l'entité Order

## Description
Créer l'entité Order pour représenter une commande passée par un festivalier ou un groupe, contenant plusieurs items, avec gestion du statut, du coût, et des tokens.

## Implementation Plan
1. Définir les attributs : id, festivalier(s), items, statut, coût, date, type (individuelle/groupe).
2. Implémenter les règles métier : validation du solde, gestion des statuts (créée, modifiée, annulée, reconnue, prête).
3. Ajouter des méthodes pour créer, modifier, annuler, et consulter une commande.
4. Ajouter des tests unitaires pour chaque règle métier.

## Gherkin Test Scenarios

### Scenario: Création de commande
Given un festivalier avec un solde suffisant
When il passe une commande
Then la commande est créée et le solde est mis à jour

### Scenario: Commande échoue pour solde insuffisant
Given un festivalier avec un solde insuffisant
When il tente de passer une commande
Then la commande échoue et un message d'erreur est retourné

### Scenario: Annulation de commande
Given une commande non reconnue
When le festivalier annule la commande
Then le solde est remboursé et la commande est annulée

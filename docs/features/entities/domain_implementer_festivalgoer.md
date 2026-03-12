# Implémenter l'entité FestivalGoer

## Description
Créer l'entité FestivalGoer pour représenter un participant au festival, avec ses balances de tokens (drink, snack), ses commandes, et ses transferts de tokens.

## Implementation Plan
1. Définir les attributs : id, nom, balances de tokens, historique de commandes, historique de transferts.
2. Implémenter les règles métier : pas de balance négative, attribution des tokens par jour, gestion des transferts.
3. Ajouter des méthodes pour consulter le solde, transférer des tokens, et gérer les commandes.
4. Ajouter des tests unitaires pour chaque règle métier.

## Gherkin Test Scenarios

### Scenario: Consultation du solde
Given un festivalier avec des tokens
When il consulte son solde
Then le solde affiché correspond à ses tokens actuels

### Scenario: Transfert de tokens
Given un festivalier avec des tokens
When il transfère des tokens à un autre festivalier
Then le solde est mis à jour et ne devient pas négatif

### Scenario: Attribution des tokens par jour
Given un festivalier le matin d'une nouvelle journée de festival
When les tokens sont attribués
Then il reçoit 9 food tokens et 6 drink tokens

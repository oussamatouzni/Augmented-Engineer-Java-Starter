# Implémenter l'entité Token

## Description
Créer l'entité Token pour représenter un jeton (drink ou snack) utilisé pour payer les commandes, avec gestion du type, du solde, et des transferts.

## Implementation Plan
1. Définir les attributs : id, type (drink/snack), solde, date d'attribution, historique de transferts.
2. Implémenter les règles métier : attribution par jour, transfert, solde non négatif.
3. Ajouter des méthodes pour attribuer, transférer, et consulter le solde.
4. Ajouter des tests unitaires pour chaque règle métier.

## Gherkin Test Scenarios

### Scenario: Attribution de tokens
Given un festivalier le matin d'une nouvelle journée
When les tokens sont attribués
Then il reçoit le bon nombre de tokens selon le type

### Scenario: Transfert de tokens
Given un festivalier avec des tokens
When il transfère des tokens à un autre festivalier
Then le solde est mis à jour et ne devient pas négatif

### Scenario: Solde non négatif
Given un festivalier
When il tente de dépenser plus de tokens qu'il n'en possède
Then la transaction échoue

# Implémenter l'entité Item

## Description
Créer l'entité Item pour représenter un article (boisson ou nourriture) pouvant être commandé, avec ses caractéristiques (type, coût, disponibilité).

## Implementation Plan
1. Définir les attributs : id, nom, type (drink/snack/meal), coût en tokens, disponibilité, catégorie (alcool/non-alcool, snack/meal).
2. Implémenter les règles métier : coût selon type, gestion de la disponibilité, catégorisation.
3. Ajouter des méthodes pour consulter le coût, vérifier la disponibilité, et catégoriser l'item.
4. Ajouter des tests unitaires pour chaque règle métier.

## Gherkin Test Scenarios

### Scenario: Consultation du coût
Given un item de type premium alcoholic drink
When on consulte le coût
Then le coût est de 2 drink tokens

### Scenario: Consultation de disponibilité
Given un item en rupture de stock
When on tente de le commander
Then la commande échoue et un message d'erreur est retourné

### Scenario: Catégorisation d'un item
Given un item
When on consulte sa catégorie
Then la catégorie correspond à son type (drink/snack/meal)

# Application - Water Notification

**Contexte**
Le barman doit envoyer des notifications régulières aux festivaliers pour leur rappeler de boire de l'eau, en fonction de l'heure et du nombre de boissons alcoolisées consommées.

**Critères d'acceptation**
Feature: Water notification
  Scenario: Hourly notification
    Given it is between 11:00 AM and 7:00 PM
    When the notification time arrives
    Then all festival goers receive a friendly reminder to drink water
  Scenario: Increased frequency for heavy drinkers
    Given a festival goer has consumed more than 3 alcoholic drinks in the past hour
    When the notification time arrives
    Then they receive reminders every 30 minutes
  Scenario: Notification outside allowed hours
    Given it is 8:00 PM
    When the notification time arrives
    Then no notification is sent

**Notes**
- Les notifications doivent encourager la consommation responsable.
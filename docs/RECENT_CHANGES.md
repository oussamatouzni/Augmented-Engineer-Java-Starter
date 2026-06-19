# Recent Changes and New Features

This document summarizes the current state of the Bel'Air's Buvette project and highlights recent changes and newly implemented features documented in this repository.

## High-level status

- Project structure follows a Hexagonal Architecture with `domain/`, `application/`, and `infrastructure/` modules.
- Gradle-based Java project (Gradle wrapper included). Target Java 21+.

## New / Notable Features (covered by FEATURES.md and docs/features/)

- Token-based economy for festival goers: drink tokens and food tokens, with rules for issuance, spending, and transfer.
- Drink ordering: supports alcoholic (normal/premium) and non-alcoholic drinks with token costs per type.
- Food ordering: distinguishes snacks (1 token) and meals (3 tokens).
- Multi-item orders: multiple items in one order with balance validation.
- Group orders: pooled tokens and group contributions supported.
- Order lifecycle operations: change, cancel, acknowledge, and mark as ready with clear business rules.
- Order acknowledgement includes an estimated time calculation based on item types and counts.
- Token transfer between users with recipient confirmation and transfer limits.
- Water notifications: periodic reminders with configurable frequency rules based on recent alcohol consumption.

## Documentation locations

- Feature definitions and acceptance criteria: [FEATURES.md](../FEATURES.md)
- Per-feature documentation: `docs/features/` (contains fine-grained docs for drink_order, food_order, group_order, token_transfer, water_notification, etc.)
- API and controllers: `application/src/main/java/` and `application/README.md` (when present).

## Tests and examples

- Integration and unit tests are in `application/src/test/` and `domain/src/test/`.
- Example test outputs and fixtures available in `application/build/test-output/`.

## Recommended next-doc steps

- Add short usage examples and request/response samples for the main REST endpoints in `application/`.
- Add a CHANGELOG.md and tag releases to track user-facing changes over time.
- Add a short migration/upgrade note if token rules change in future versions.

Generated on 2026-06-19.

# Git Guidelines

## 1. Branching Strategy
- Use main as the trunk branch for integration and deployment.
- Create short-lived feature branches for new features, bug fixes, or experiments.
- Name feature branches descriptively (e.g., feature/login-api, fix/order-bug, chore/update-docs).

## 2. Feature Branch Workflow
- Branch from main for each new task.
- Keep feature branches small and focused; merge frequently.
- Rebase feature branches onto main before merging to avoid conflicts.

## 3. Commit Conventions
- Use conventional commit prefixes:
  - feat: for new features
  - fix: for bug fixes
  - chore: for maintenance tasks
  - test: for adding or updating tests
  - docs: for documentation changes
- Write concise, meaningful commit messages (e.g., feat: add order validation).

## 4. Pull Requests & Reviews
- Open a pull request for each feature branch.
- Request review from teammates before merging.
- Address feedback and update PRs as needed.

## 5. Merging & Integration
- Merge feature branches into main after review and successful CI.
- Delete feature branches after merging.

## 6. Best Practices
- Avoid large, long-lived branches.
- Commit early and often.
- Keep main always deployable.

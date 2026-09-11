# Learning Path

This file keeps the implementation work aligned with the learning sequence.

## Completed

- Project foundation
- Spring Boot mental model
- Controller / Service / Repository
- Dependency Injection
- DTOs and validation
- JPA / Hibernate
- Transactions and persistence context
- Dirty checking
- Database relationships and N+1
- REST API design
- Authentication and authorization concepts
- Spring Security baseline
- Session authentication
- CSRF protection
- Secure baseline merged to main

## Current

- Continuous Integration with GitHub Actions
  - clean runner environment
  - backend build and tests
  - frontend lint and build
  - pull request checks

## Next

1. Make CI green and understand any failures.
2. Add a committed pnpm lockfile for reproducible frontend installs.
3. Configure Heroku runtime architecture.
4. Provision production PostgreSQL.
5. Configure production environment variables and secrets.
6. Add Continuous Deployment from main.
7. Add post-deploy health and smoke checks.
8. Return to product development:
   - frontend/backend authentication integration
   - admin project management
   - blog
   - media storage
   - broader testing
   - observability
   - later performance, caching, queues, and AI features only when justified.

## Principle

Need -> concept -> implementation -> observed problem -> next concept.

Infrastructure topics should be introduced only when they solve a real requirement in this project.

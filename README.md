# Personal Platform

A learning-first personal developer platform built as a real full-stack system.

## Stack

- Next.js + TypeScript
- Java 25 + Spring Boot 4.1.1
- PostgreSQL
- Flyway
- Spring Data JPA / Hibernate
- Spring Security
- Docker Compose

## Current baseline

The repository currently contains the Projects vertical slice plus an authentication baseline:

- public project reads
- protected project mutations
- database-backed ADMIN account
- password hashing through Spring Security
- server-side session authentication
- CSRF protection
- a simple Next.js admin login page
- PostgreSQL migrations for projects and users

## Local run

Start PostgreSQL:

```bash
docker compose -f infra/docker-compose.yml up -d
```

Configure the initial administrator:

```bash
export ADMIN_EMAIL="admin@example.com"
export ADMIN_PASSWORD="change-this-password"
```

Run the API:

```bash
cd apps/api
mvn spring-boot:run
```

Create the web environment file:

```bash
cp apps/web/.env.local.example apps/web/.env.local
```

Run the web app:

```bash
pnpm install
pnpm web:dev
```

Web: http://localhost:3000

API: http://localhost:8080

Admin login: http://localhost:3000/admin/login

## Security model

Public:

```text
GET /api/projects
GET /api/projects/{id}
GET /actuator/health
GET /api/auth/csrf
```

ADMIN only:

```text
POST   /api/projects
PATCH  /api/projects/{id}
DELETE /api/projects/{id}
POST   /api/projects/{id}/publish
```

This is intentionally still a small architecture. CI/CD, Heroku deployment, production session infrastructure, observability, media storage, and additional modules are the next learning steps.

# Architecture

## Runtime view

```text
Browser
  |
  v
Next.js
  |
  | HTTP/JSON + session cookie + CSRF token
  v
Spring Security Filter Chain
  |
  v
ProjectController
  |
  v
ProjectService
  |
  v
ProjectRepository
  |
  v
PostgreSQL
```

## Current design

The code intentionally starts with a conventional layered Spring architecture:

```text
Controller -> Service -> Repository -> Database
```

The Project class is currently both the domain object and the JPA entity. This is deliberate: the project will be refactored only when real requirements justify stronger boundaries.

## Authentication flow

```text
GET /api/auth/csrf
  -> receive CSRF token

POST /login
  -> Spring Security
  -> DatabaseUserDetailsService
  -> AppUserRepository
  -> PasswordEncoder
  -> SecurityContext
  -> HTTP Session

Later requests
  -> JSESSIONID cookie
  -> session restores Authentication
  -> authorization rules run before controllers
```

## Next architecture steps

- CI with GitHub Actions
- Heroku deployment
- production configuration and secrets
- production database
- observability
- stronger tests
- additional modules

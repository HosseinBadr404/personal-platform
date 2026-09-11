CREATE TABLE projects (
    id UUID PRIMARY KEY,
    title VARCHAR(120) NOT NULL,
    slug VARCHAR(160) NOT NULL UNIQUE,
    short_description VARCHAR(240) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(32) NOT NULL,
    type VARCHAR(32) NOT NULL,
    repository_url TEXT,
    live_url TEXT,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    published_at TIMESTAMPTZ
);

CREATE INDEX idx_projects_status ON projects(status);
CREATE INDEX idx_projects_published_at ON projects(published_at DESC);

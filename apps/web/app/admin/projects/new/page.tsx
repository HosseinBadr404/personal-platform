'use client';

import { FormEvent, useState } from 'react';

const API_ORIGIN = process.env.NEXT_PUBLIC_API_ORIGIN ?? 'http://localhost:8080';
const API_BASE_URL = `${API_ORIGIN}/api`;

type CsrfResponse = {
  token: string;
  headerName: string;
};

async function loadCsrf(): Promise<CsrfResponse> {
  const response = await fetch(`${API_BASE_URL}/auth/csrf`, {
    credentials: 'include',
  });

  if (!response.ok) {
    throw new Error('Could not initialize request security.');
  }

  return response.json();
}

export default function NewProjectPage() {
  const [message, setMessage] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setSubmitting(true);
    setMessage(null);

    const formElement = event.currentTarget;
    const form = new FormData(formElement);
    const payload = {
      title: form.get('title'),
      shortDescription: form.get('shortDescription'),
      description: form.get('description'),
      type: form.get('type'),
      repositoryUrl: form.get('repositoryUrl'),
      liveUrl: form.get('liveUrl'),
    };

    try {
      const csrf = await loadCsrf();

      const response = await fetch(`${API_BASE_URL}/projects`, {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json',
          [csrf.headerName]: csrf.token,
        },
        body: JSON.stringify(payload),
      });

      if (response.status === 401 || response.status === 403) {
        throw new Error('Sign in as the administrator first.');
      }

      if (!response.ok) {
        const error = await response.json().catch(() => null);
        throw new Error(error?.message ?? 'Could not create project.');
      }

      formElement.reset();
      setMessage('Project created as DRAFT.');
    } catch (reason) {
      setMessage(reason instanceof Error ? reason.message : 'Unknown error');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <section className="form-section">
      <p className="eyebrow">ADMIN</p>
      <h1>Create Project</h1>
      <p className="muted">This action requires an authenticated ADMIN session.</p>

      <form className="form" onSubmit={handleSubmit}>
        <label>
          Title
          <input name="title" required maxLength={120} />
        </label>

        <label>
          Short description
          <input name="shortDescription" required maxLength={240} />
        </label>

        <label>
          Full description
          <textarea name="description" required rows={7} />
        </label>

        <label>
          Type
          <select name="type" defaultValue="PROJECT">
            <option value="PROJECT">Project</option>
            <option value="APP">App</option>
            <option value="EXPERIMENT">Experiment</option>
            <option value="OPEN_SOURCE">Open source</option>
          </select>
        </label>

        <label>
          Repository URL
          <input name="repositoryUrl" type="url" />
        </label>

        <label>
          Live URL
          <input name="liveUrl" type="url" />
        </label>

        <button className="button" disabled={submitting} type="submit">
          {submitting ? 'Creating…' : 'Create project'}
        </button>
      </form>

      {message && <div className="notice">{message}</div>}
    </section>
  );
}

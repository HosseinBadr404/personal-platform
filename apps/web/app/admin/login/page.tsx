'use client';

import { FormEvent, useState } from 'react';
import { useRouter } from 'next/navigation';

const API_ORIGIN = process.env.NEXT_PUBLIC_API_ORIGIN ?? 'http://localhost:8080';

type CsrfResponse = {
  token: string;
  headerName: string;
};

export default function AdminLoginPage() {
  const router = useRouter();
  const [message, setMessage] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setSubmitting(true);
    setMessage(null);

    const form = new FormData(event.currentTarget);
    const username = String(form.get('email') ?? '');
    const password = String(form.get('password') ?? '');

    try {
      const csrfResponse = await fetch(`${API_ORIGIN}/api/auth/csrf`, {
        credentials: 'include',
      });

      if (!csrfResponse.ok) {
        throw new Error('Could not initialize login security.');
      }

      const csrf: CsrfResponse = await csrfResponse.json();
      const body = new URLSearchParams({ username, password });

      const response = await fetch(`${API_ORIGIN}/login`, {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
          [csrf.headerName]: csrf.token,
        },
        body,
      });

      if (!response.ok) {
        throw new Error('Invalid email or password.');
      }

      router.push('/admin/projects/new');
      router.refresh();
    } catch (reason) {
      setMessage(reason instanceof Error ? reason.message : 'Login failed.');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <section className="form-section">
      <p className="eyebrow">ADMIN</p>
      <h1>Sign in</h1>
      <p className="muted">Use the administrator account configured on the API.</p>

      <form className="form" onSubmit={handleSubmit}>
        <label>
          Email
          <input name="email" type="email" required autoComplete="username" />
        </label>

        <label>
          Password
          <input name="password" type="password" required autoComplete="current-password" />
        </label>

        <button className="button" disabled={submitting} type="submit">
          {submitting ? 'Signing in…' : 'Sign in'}
        </button>
      </form>

      {message && <div className="notice">{message}</div>}
    </section>
  );
}

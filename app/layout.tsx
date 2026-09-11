import type { Metadata } from 'next';
import type { ReactNode } from 'react';
import './globals.css';

export const metadata: Metadata = {
  title: 'Personal Platform',
  description: 'Projects, experiments, writing, and apps.',
};

export default function RootLayout({ children }: Readonly<{ children: ReactNode }>) {
  return (
    <html lang="en">
      <body>
        <header className="site-header">
          <a href="/" className="brand">Personal Platform</a>
          <nav>
            <a href="/">Projects</a>
            <a href="/admin/login">Admin</a>
          </nav>
        </header>
        <main className="container">{children}</main>
      </body>
    </html>
  );
}

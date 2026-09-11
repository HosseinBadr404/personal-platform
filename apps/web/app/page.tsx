import { getProjects, type Project } from '@/lib/projects';

export const dynamic = 'force-dynamic';

export default async function HomePage() {
  let projects: Project[] = [];
  let error: string | null = null;

  try {
    projects = await getProjects();
  } catch (reason) {
    error = reason instanceof Error ? reason.message : 'Unknown API error';
  }

  return (
    <>
      <section className="hero">
        <p className="eyebrow">LEARNING PROJECT</p>
        <h1>Personal Developer Platform</h1>
        <p>
          A real project for learning software architecture, Spring Boot,
          databases, security, deployment, and production engineering.
        </p>
      </section>

      <section>
        <div className="section-title">
          <h2>Projects</h2>
          <a className="button secondary" href="/admin/login">Admin</a>
        </div>

        {error ? (
          <div className="notice">API is not reachable: {error}</div>
        ) : projects.length === 0 ? (
          <div className="notice">No projects yet.</div>
        ) : (
          <div className="grid">
            {projects.map((project) => (
              <article className="card" key={project.id}>
                <div className="badges">
                  <span>{project.type}</span>
                  <span>{project.status}</span>
                </div>
                <h3>{project.title}</h3>
                <p>{project.shortDescription}</p>
                <div className="links">
                  {project.liveUrl && <a href={project.liveUrl}>Live</a>}
                  {project.repositoryUrl && <a href={project.repositoryUrl}>Repository</a>}
                </div>
              </article>
            ))}
          </div>
        )}
      </section>
    </>
  );
}

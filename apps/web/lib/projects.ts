export type ProjectStatus = 'DRAFT' | 'PUBLISHED' | 'ARCHIVED';
export type ProjectType = 'APP' | 'PROJECT' | 'EXPERIMENT' | 'OPEN_SOURCE';

export type Project = {
  id: string;
  title: string;
  slug: string;
  shortDescription: string;
  description: string;
  status: ProjectStatus;
  type: ProjectType;
  repositoryUrl: string | null;
  liveUrl: string | null;
  featured: boolean;
  createdAt: string;
  updatedAt: string;
  publishedAt: string | null;
};

export async function getProjects(): Promise<Project[]> {
  const apiBaseUrl = process.env.API_BASE_URL ?? 'http://localhost:8080/api';
  const response = await fetch(`${apiBaseUrl}/projects`, { cache: 'no-store' });

  if (!response.ok) {
    throw new Error(`Could not load projects: ${response.status}`);
  }

  return response.json();
}

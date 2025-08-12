"use client";

import { useEffect, useState } from "react";
import { gql } from "graphql-request";
import { createClient } from "@/lib/graphqlClient";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";

const ME = gql`query { me { id role } }`;
const CREATE_JOB = gql`
  mutation CreateJob($title: String!, $description: String!, $location: String) {
    createJob(title: $title, description: $description, location: $location) { id title }
  }
`;
const JOBS = gql`query { jobs { id title } }`;
const JOB_APPS = gql`query Apps($jobId: Int!) { jobApplications(jobId: $jobId) { id userId resumeText } }`;

type Job = { id: number; title: string };
type JobsResponse = { jobs: Job[] };
type Application = { id: number; userId: number; resumeText: string };
type AppsResponse = { jobApplications: Application[] };

function getErrorMessage(err: unknown): string {
  if (typeof err === "string") return err;
  if (err && typeof err === "object") {
    const anyErr = err as { response?: { errors?: Array<{ message?: string }> }; message?: string };
    return anyErr.response?.errors?.[0]?.message ?? anyErr.message ?? "Request failed";
  }
  return "Request failed";
}

export default function BusinessJobsPage() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [location, setLocation] = useState("");
  const [jobs, setJobs] = useState<Job[]>([]);
  const [apps, setApps] = useState<Record<number, Application[]>>({});
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    (async () => {
      try {
        const client = createClient();
        await client.request(ME);
        const data = await client.request<JobsResponse>(JOBS);
        setJobs(data.jobs ?? []);
      } catch (err: unknown) {
        setError(getErrorMessage(err));
      }
    })();
  }, []);

  const create = async () => {
    setError(null);
    try {
      const client = createClient();
      await client.request(CREATE_JOB, { title, description, location });
      const data = await client.request<JobsResponse>(JOBS);
      setJobs(data.jobs ?? []);
      setTitle(""); setDescription(""); setLocation("");
    } catch (err: unknown) { setError(getErrorMessage(err)); }
  };

  const loadApps = async (jobId: number) => {
    try {
      const client = createClient();
      const data = await client.request<AppsResponse>(JOB_APPS, { jobId });
      setApps((prev) => ({ ...prev, [jobId]: data.jobApplications ?? [] }));
    } catch (err: unknown) { setError(getErrorMessage(err)); }
  };

  return (
    <div className="container mx-auto p-6 space-y-6">
      <h1 className="text-2xl font-semibold">Business Jobs</h1>
      {error && <div className="text-red-600 text-sm">{error}</div>}

      <Card>
        <CardHeader><CardTitle>Create Job</CardTitle></CardHeader>
        <CardContent className="space-y-2">
          <Input placeholder="Title" value={title} onChange={(e) => setTitle(e.target.value)} />
          <Input placeholder="Description" value={description} onChange={(e) => setDescription(e.target.value)} />
          <Input placeholder="Location" value={location} onChange={(e) => setLocation(e.target.value)} />
          <Button onClick={create}>Create</Button>
        </CardContent>
      </Card>

      <div className="grid gap-4 md:grid-cols-2">
        {jobs.map((job) => (
          <Card key={job.id}>
            <CardHeader><CardTitle>{job.title}</CardTitle></CardHeader>
            <CardContent>
              <Button variant="outline" onClick={() => loadApps(job.id)}>View Applications</Button>
              <ul className="mt-3 space-y-1">
                {(apps[job.id] ?? []).map((a) => (
                  <li key={a.id} className="text-sm">Applicant #{a.userId}: {a.resumeText.slice(0, 60)}</li>
                ))}
              </ul>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}

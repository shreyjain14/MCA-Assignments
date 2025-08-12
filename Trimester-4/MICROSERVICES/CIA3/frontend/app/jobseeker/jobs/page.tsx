"use client";

import { useEffect, useState } from "react";
import { gql } from "graphql-request";
import { createClient } from "@/lib/graphqlClient";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";

const QUERY = gql`
  query Jobs { jobs { id title description isActive } }
`;

const APPLY = gql`
  mutation Apply($jobId: Int!, $resumeText: String!) {
    applyToJob(jobId: $jobId, resumeText: $resumeText) { id jobId }
  }
`;

type Job = { id: number; title: string; description: string; isActive: boolean };
function getErrorMessage(err: unknown): string {
  if (typeof err === "string") return err;
  if (err && typeof err === "object") {
    const anyErr = err as { response?: { errors?: Array<{ message?: string }> }; message?: string };
    return anyErr.response?.errors?.[0]?.message ?? anyErr.message ?? "Request failed";
  }
  return "Request failed";
}

export default function JobsPage() {
  const [jobs, setJobs] = useState<Job[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    (async () => {
      try {
        const client = createClient();
        const data = await client.request<{ jobs: Job[] }>(QUERY);
        setJobs(data.jobs ?? []);
      } catch (err: unknown) {
        setError(getErrorMessage(err));
      }
    })();
  }, []);

  const apply = async (id: number) => {
    setError(null);
    try {
      const client = createClient();
      await client.request(APPLY, { jobId: id, resumeText: "My resume" });
      alert("Applied!");
    } catch (err: unknown) {
      setError(getErrorMessage(err));
    }
  };

  return (
    <div className="container mx-auto p-6 space-y-4">
      <h1 className="text-2xl font-semibold">Jobs</h1>
      {error && <div className="text-red-600 text-sm">{error}</div>}
      <div className="grid gap-4 md:grid-cols-2">
        {jobs.map((job) => (
          <Card key={job.id}>
            <CardHeader><CardTitle>{job.title}</CardTitle></CardHeader>
            <CardContent>
              <p className="mb-2 text-sm text-muted-foreground">{job.description}</p>
              <Button onClick={() => apply(job.id)}>Apply</Button>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}

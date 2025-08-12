"use client";

import { useEffect, useState } from "react";
import { gql } from "graphql-request";
import { createClient } from "@/lib/graphqlClient";

const QUERY = gql`
  query MyApplications { myApplications { id jobId resumeText } }
`;

type Application = { id: number; jobId: number; resumeText: string };

function getErrorMessage(err: unknown): string {
  if (typeof err === "string") return err;
  if (err && typeof err === "object") {
    const anyErr = err as { response?: { errors?: Array<{ message?: string }> }; message?: string };
    return anyErr.response?.errors?.[0]?.message ?? anyErr.message ?? "Request failed";
  }
  return "Request failed";
}

export default function MyApplicationsPage() {
  const [apps, setApps] = useState<Application[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    (async () => {
      try {
        const client = createClient();
        const data = await client.request<{ myApplications: Application[] }>(QUERY);
        setApps(data.myApplications ?? []);
      } catch (err: unknown) {
        setError(getErrorMessage(err));
      }
    })();
  }, []);

  return (
    <div className="container mx-auto p-6 space-y-4">
      <h1 className="text-2xl font-semibold">My Applications</h1>
      {error && <div className="text-red-600 text-sm">{error}</div>}
      <ul className="list-disc pl-5 space-y-2">
        {apps.map((a) => (
          <li key={a.id}>Job #{a.jobId} — {a.resumeText.slice(0, 60)}</li>
        ))}
      </ul>
    </div>
  );
}

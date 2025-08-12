"use client";

import { useEffect, useState } from "react";
import { gql } from "graphql-request";
import { createClient } from "@/lib/graphqlClient";

type User = { id: number; email: string; role: string };
const QUERY = gql`query { adminUsers { id email role } }`;

function getErrorMessage(err: unknown): string {
  if (typeof err === "string") return err;
  if (err && typeof err === "object") {
    const anyErr = err as { response?: { errors?: Array<{ message?: string }> }; message?: string };
    return anyErr.response?.errors?.[0]?.message ?? anyErr.message ?? "Request failed";
  }
  return "Request failed";
}

export default function AdminUsersPage() {
  const [users, setUsers] = useState<User[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    (async () => {
      try {
        const client = createClient();
        const data = await client.request<{ adminUsers: User[] }>(QUERY);
        setUsers(data.adminUsers ?? []);
      } catch (err: unknown) { setError(getErrorMessage(err)); }
    })();
  }, []);

  return (
    <div className="container mx-auto p-6 space-y-4">
      <h1 className="text-2xl font-semibold">Users</h1>
      {error && <div className="text-red-600 text-sm">{error}</div>}
      <ul className="list-disc pl-5 space-y-1">
        {users.map((u) => (<li key={u.id}>{u.email} — {u.role}</li>))}
      </ul>
    </div>
  );
}

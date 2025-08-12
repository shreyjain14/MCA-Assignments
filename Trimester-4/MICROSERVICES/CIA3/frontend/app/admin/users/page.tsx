"use client";

import { useEffect, useState } from "react";
import { gql } from "graphql-request";
import { createClient } from "@/lib/graphqlClient";

const QUERY = gql`query { adminUsers { id email role } }`;

export default function AdminUsersPage() {
  const [users, setUsers] = useState<any[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    (async () => {
      try {
        const client = createClient();
        const data = await client.request(QUERY);
        setUsers(data.adminUsers ?? []);
      } catch (err: any) { setError(err?.response?.errors?.[0]?.message ?? err.message); }
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

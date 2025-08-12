"use client";

import { useState } from "react";
import { GraphQLClient, gql } from "graphql-request";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

const endpoint = process.env.NEXT_PUBLIC_GATEWAY_URL ?? "http://localhost:8000/graphql";

const REGISTER = gql`
  mutation Register($email: String!, $password: String!) {
    register(email: $email, password: $password, role: "business") { id email role }
  }
`;

export default function RegisterBusinessPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [ok, setOk] = useState<boolean>(false);

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null); setOk(false);
    try {
      const client = new GraphQLClient(endpoint);
      await client.request(REGISTER, { email, password });
      setOk(true);
    } catch (err: any) {
      setError(err?.response?.errors?.[0]?.message ?? err.message ?? "Registration failed");
    }
  };

  return (
    <div className="mx-auto max-w-md p-6">
      <Card>
        <CardHeader>
          <CardTitle>Register as Business</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={onSubmit} className="space-y-4">
            <Input placeholder="Email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
            <Input placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
            {error && <div className="text-red-600 text-sm">{error}</div>}
            {ok && <div className="text-green-600 text-sm">Registered! You can now login.</div>}
            <Button type="submit" className="w-full">Register</Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}

"use client";

import { useState } from "react";
import { GraphQLClient, gql } from "graphql-request";
import Cookies from "js-cookie";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

const endpoint = process.env.NEXT_PUBLIC_GATEWAY_URL ?? "http://localhost:8000/graphql";

const LOGIN = gql`
  mutation Login($email: String!, $password: String!) {
    login(email: $email, password: $password) {
      accessToken
      tokenType
    }
  }
`;

type LoginResponse = { login: { accessToken: string; tokenType: string } };

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    try {
      const client = new GraphQLClient(endpoint);
      const data = await client.request<LoginResponse>(LOGIN, { email, password });
      const token = data.login.accessToken;
      Cookies.set("token", token, { sameSite: "lax" });
      window.location.href = "/";
    } catch (err: any) {
      setError(err?.response?.errors?.[0]?.message ?? err.message ?? "Login failed");
    }
  };

  return (
    <div className="mx-auto max-w-md p-6">
      <Card>
        <CardHeader>
          <CardTitle>Login</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={onSubmit} className="space-y-4">
            <Input placeholder="Email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
            <Input placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
            {error && <div className="text-red-600 text-sm">{error}</div>}
            <Button type="submit" className="w-full">Login</Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}

"use client";

import { GraphQLClient } from "graphql-request";
import Cookies from "js-cookie";

export function createClient() {
  const endpoint = process.env.NEXT_PUBLIC_GATEWAY_URL ?? "http://localhost:8000/graphql";
  const token = Cookies.get("token");
  const headers: Record<string, string> = {};
  if (token) headers["Authorization"] = `Bearer ${token}`;
  return new GraphQLClient(endpoint, { headers });
}

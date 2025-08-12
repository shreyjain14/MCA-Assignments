"use client";

import Link from "next/link";
import Cookies from "js-cookie";
import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";

export default function Navbar() {
  const [authed, setAuthed] = useState<boolean>(false);

  useEffect(() => {
    setAuthed(Boolean(Cookies.get("token")));
  }, []);

  const logout = () => {
    Cookies.remove("token");
    window.location.href = "/";
  };

  return (
    <nav className="w-full border-b bg-background">
      <div className="container mx-auto p-4 flex items-center gap-4">
        <Link href="/" className="font-semibold">Job Board</Link>
        <div className="ml-auto flex gap-3">
          <Link href="/jobseeker/jobs">Jobs</Link>
          <Link href="/jobseeker/applications">My Applications</Link>
          <Link href="/business/jobs">Business</Link>
          <Link href="/admin/users">Admin</Link>
          {!authed ? (
            <Link href="/login">Login</Link>
          ) : (
            <Button size="sm" variant="outline" onClick={logout}>Logout</Button>
          )}
        </div>
      </div>
    </nav>
  );
}

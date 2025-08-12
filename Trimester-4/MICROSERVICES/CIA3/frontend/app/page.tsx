import Link from "next/link";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";

export default function Home() {
  return (
    <main className="container mx-auto p-6 grid gap-6 md:grid-cols-3">
      <Card>
        <CardHeader><CardTitle>Jobseekers</CardTitle></CardHeader>
        <CardContent>
          <ul className="space-y-2">
            <li><Link className="underline" href="/register/jobseeker">Register (Jobseeker)</Link></li>
            <li><Link className="underline" href="/login">Login</Link></li>
            <li><Link className="underline" href="/jobseeker/jobs">Browse Jobs</Link></li>
            <li><Link className="underline" href="/jobseeker/applications">My Applications</Link></li>
          </ul>
        </CardContent>
      </Card>
      <Card>
        <CardHeader><CardTitle>Businesses</CardTitle></CardHeader>
        <CardContent>
          <ul className="space-y-2">
            <li><Link className="underline" href="/register/business">Register (Business)</Link></li>
            <li><Link className="underline" href="/login">Login</Link></li>
            <li><Link className="underline" href="/business/jobs">Manage Jobs</Link></li>
          </ul>
        </CardContent>
      </Card>
      <Card>
        <CardHeader><CardTitle>Admin</CardTitle></CardHeader>
        <CardContent>
          <ul className="space-y-2">
            <li><Link className="underline" href="/admin/users">Users</Link></li>
          </ul>
        </CardContent>
      </Card>
    </main>
  );
}

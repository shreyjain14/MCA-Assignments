# Job Board Microservices (FastAPI + PostgreSQL + Docker)

A minimal microservice-based Job Board application built with FastAPI and PostgreSQL. No Docker required.

Services:
- Auth Service: user registration, login, JWT issuance (also provides gRPC token verification)
- Jobseeker Service: jobseeker profiles and applications
- Business Service: company and job postings (also provides gRPC job lookups and owner views)
- Admin Service: user management (roles) via auth database
- Gateway Service: GraphQL API over the other services using REST (mounted at `/graphql`)

Each service runs independently with its own FastAPI app and database. JWT can be shared across services using the same secret or validated centrally via Auth (gRPC). The GraphQL gateway forwards Authorization headers to the underlying REST services.

## Run with Docker

- Prerequisites: Docker Desktop (or Docker Engine + Compose v2)

Steps:
1) Build and start all services (PostgreSQL, microservices, gateway, frontend):
```bash
docker compose up -d --build
```
2) Open:
- Frontend: http://localhost:3000
- GraphQL: http://localhost:8000/graphql
- REST docs: http://localhost:8001/docs, http://localhost:8002/docs, http://localhost:8003/docs, http://localhost:8004/docs

3) Logs and lifecycle:
```bash
# follow logs for selected services
docker compose logs -f gateway frontend

# rebuild one service
docker compose up -d --build gateway

# stop everything
docker compose down

# stop and remove DB volume (resets data)
docker compose down -v
```

Configuration notes:
- The Compose file sets service-to-service URLs and gRPC addresses automatically. You can change JWT secrets and CORS in `docker-compose.yml`.
- To allow additional frontend origins, set `gateway.environment.CORS_ORIGINS` to a comma-separated list (e.g., `http://localhost:3000,http://localhost:3001`).
- Databases are created on first run via `scripts/init.sql`.

Production build (frontend):
- Current image runs Next.js in dev mode for convenience. For production, adjust `frontend/Dockerfile` to build and run `npm run start`.


## Deploy to AWS EC2 (Docker)

1) Provision an Ubuntu EC2 instance and open security group ports you need (e.g., 3000, 8000; avoid exposing 5432).
2) SSH in and install Docker + Compose:
```bash
sudo apt-get update -y && sudo apt-get install -y ca-certificates curl gnupg
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo $VERSION_CODENAME) stable" | sudo tee /etc/apt/sources.list.d/docker.list >/dev/null
sudo apt-get update -y
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo systemctl enable --now docker
sudo usermod -aG docker $USER && newgrp docker
```
3) Clone and run:
```bash
git clone https://github.com/shreyjain14/MCA-Assignments.git
cd Trimester-4/MICROSERVICES/CIA3
# optional: edit docker-compose.yml
# - set strong JWT_SECRET values
# - set gateway CORS_ORIGINS to include your origin, e.g., http://<ip-or-domain>:3000
# - set frontend NEXT_PUBLIC_GATEWAY_URL to http://<ip-or-domain>:8000/graphql

docker compose pull
docker compose up -d --build
```
4) Visit:
- Frontend: `http://<ip-or-domain>:3000`
- GraphQL: `http://<ip-or-domain>:8000/graphql`

Troubleshooting:
- CORS: add your exact frontend origin to `CORS_ORIGINS` and rebuild gateway: `docker compose up -d --build gateway`
- Logs: `docker compose logs -f gateway frontend`
- Rebuild all: `docker compose up -d --build`

## Run Manually

### 1) Prerequisites
- Python 3.10+
- PostgreSQL 13+
- PowerShell (Windows) or any shell

### 2) Create Databases
Create four databases (you can use any names, update `.env` accordingly):
- `auth_db`
- `jobseeker_db`
- `business_db`
- `admin_db` (admin service can reuse `auth_db`)

Example using psql:
```sh
psql -U postgres -h localhost -c "CREATE DATABASE auth_db;"
psql -U postgres -h localhost -c "CREATE DATABASE jobseeker_db;"
psql -U postgres -h localhost -c "CREATE DATABASE business_db;"
psql -U postgres -h localhost -c "CREATE DATABASE admin_db;"
```

### 3) Configure Environment
Copy `env.example` to `.env` inside each service folder and set values:
- `DATABASE_URL` for that service (not needed for gateway)
- `JWT_SECRET` for each service (can differ when using gRPC verification)
- Auth: `GRPC_BIND` (default `0.0.0.0:50051`)
- Business: `GRPC_BIND` (default `0.0.0.0:50052`)
- Jobseeker: `GRPC_BIND` (default `0.0.0.0:50053`), `BUSINESS_GRPC_ADDR` (default `localhost:50052`)
- Gateway: base URLs for services:
  - `AUTH_BASE_URL=http://localhost:8001`
  - `JOBSEEKER_BASE_URL=http://localhost:8002`
  - `BUSINESS_BASE_URL=http://localhost:8003`
  - `ADMIN_BASE_URL=http://localhost:8004`

Connection string example:
```
DATABASE_URL=postgresql+psycopg2://postgres:YOUR_PASSWORD@localhost:5432/auth_db
JWT_SECRET=change_me_to_a_long_random_secret
JWT_EXPIRES_MINUTES=60
```

### 4) Install Dependencies
In multiple terminals (one per service), run:
```sh
cd services/auth_service && python -m venv .venv && .venv/Scripts/activate && pip install -r requirements.txt
cd services/jobseeker_service && python -m venv .venv && .venv/Scripts/activate && pip install -r requirements.txt
cd services/business_service && python -m venv .venv && .venv/Scripts/activate && pip install -r requirements.txt
cd services/admin_service && python -m venv .venv && .venv/Scripts/activate && pip install -r requirements.txt
cd services/gateway_service && python -m venv .venv && .venv/Scripts/activate && pip install -r requirements.txt
```

### 5) Run Services
Use different ports:
```sh
# Auth
uvicorn app.main:app --reload --port 8001
# Jobseeker
uvicorn app.main:app --reload --port 8002
# Business
uvicorn app.main:app --reload --port 8003
# Admin
uvicorn app.main:app --reload --port 8004
# Gateway (GraphQL)
uvicorn app.main:app --reload --port 8000
```

Or use the .bat scripts in `scripts/`, including `run_gateway.bat`.

On first run, each service will create its own tables automatically.

## REST API Reference

### Auth Service (http://localhost:8001)
- `POST /register` — Register user
  - body: `{ "email": string, "password": string, "role": "jobseeker|business|admin" }`
  - 201: `User`
- `POST /login` — Login and receive JWT
  - body: `{ "email": string, "password": string }`
  - 200: `{ "access_token": string, "token_type": "bearer" }`
- `GET /me` — Current user (requires `Authorization: Bearer <token>`) → `User`
- Debug (optional): `GET /_debug/jwt` → `{ secret_digest, grpc }`

### Jobseeker Service (http://localhost:8002)
- `GET /profiles/me` — Get current jobseeker profile (role: jobseeker) → `Profile`
- `PUT /profiles/me` — Upsert profile (role: jobseeker)
  - body: `{ "full_name": string, "bio": string | null }`
  - 200: `Profile`
- `POST /applications` — Apply to a job (role: jobseeker)
  - body: `{ "job_id": number, "resume_text": string }`
  - Validates job existence via Business gRPC
  - 201: `Application`
- `GET /applications` — List my applications (role: jobseeker) → `Application[]`
- Debug (optional): `GET /_debug/jwt`, `GET /_debug/auth_grpc`

### Business Service (http://localhost:8003)
- `POST /companies` — Create or update company (role: business)
  - body: `{ "name": string, "description": string | null }`
  - 200: `Company`
- `GET /companies/me` — Get my company (role: business) → `Company`
- `POST /jobs` — Create job (role: business)
  - body: `{ "title": string, "description": string, "location": string | null, "is_active": boolean }`
  - 201: `Job`
- `GET /jobs` — Public list jobs → `Job[]`
- `GET /jobs/{job_id}` — Public get job by id → `Job`
- `GET /jobs/{job_id}/applications` — List applications for a job (role: business; must be owner)
  - returns: `Application[]`
- Debug (optional): `GET /_debug/jwt`, `GET /_debug/auth_grpc`

### Admin Service (http://localhost:8004)
- `GET /users` — List users (role: admin) → `User[]`
- `PATCH /users/{user_id}/role` — Change user role (role: admin)
  - body: `{ "role": "jobseeker|business|admin" }`
  - 200: `User`
- Debug (optional): `GET /_debug/jwt`, `GET /_debug/auth_grpc`

## GraphQL Gateway (http://localhost:8000/graphql)

Notes:
- Field names are camelCased by default (e.g., `accessToken`, `tokenType`).
- Send `Authorization: Bearer <token>` for protected queries/mutations.

### Types
- `type User { id: Int!, email: String!, role: String! }`
- `type Job { id: Int!, ownerUserId: Int!, title: String!, description: String!, location: String, isActive: Boolean! }`
- `type Profile { userId: Int!, fullName: String!, bio: String }`
- `type Application { id: Int!, userId: Int!, jobId: Int!, resumeText: String! }`
- `type Company { userId: Int!, name: String!, description: String }`
- `type Token { accessToken: String!, tokenType: String! }`

### Queries
- `me: User` — current user
- `jobs: [Job!]!` — public job list
- `job(id: Int!): Job` — job by id
- `myApplications: [Application!]!` — current user’s applications (jobseeker)
- `myProfile: Profile` — current user’s profile (jobseeker)

Example:
```graphql
query {
  me { id email role }
  jobs { id title isActive }
  job(id: 1) { id title description }
}
```

### Mutations
- `register(email: String!, password: String!, role: String!): User!`
- `login(email: String!, password: String!): Token!`
- `upsertProfile(fullName: String!, bio: String): Profile!` (jobseeker)
- `applyToJob(jobId: Int!, resumeText: String!): Application!` (jobseeker)
- `createCompany(name: String!, description: String): Company!` (business)
- `createJob(title: String!, description: String!, location: String, isActive: Boolean = true): Job!` (business)

Examples:
```graphql
mutation {
  login(email: "you@example.com", password: "pass") { accessToken tokenType }
}
```
```graphql
mutation {
  upsertProfile(fullName: "Jane Doe", bio: "Golang dev") { userId fullName }
}
```
```graphql
mutation {
  applyToJob(jobId: 1, resumeText: "Here is my resume") { id jobId userId }
}
```
```graphql
mutation {
  createJob(title: "SDE", description: "Work on APIs", location: "Remote", isActive: true) { id title }
}
```

## Notes
- With gRPC verification, services can have different `JWT_SECRET` values; only Auth’s secret is authoritative.
- The gateway uses REST calls and does not perform its own JWT validation.

## Troubleshooting
- If GraphQL calls fail, check the base URLs in the gateway `.env` and ensure the target services are running.
- For token issues, verify Auth’s `/_debug/jwt` and the other services’ `/_debug/jwt`/`/_debug/auth_grpc` endpoints.

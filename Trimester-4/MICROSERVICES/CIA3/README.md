# Job Board Microservices (FastAPI + PostgreSQL)

A minimal microservice-based Job Board application built with FastAPI and PostgreSQL. No Docker required.

Services:
- Auth Service: user registration, login, JWT issuance
- Jobseeker Service: jobseeker profiles and applications
- Business Service: company and job postings
- Admin Service: user management (roles) via auth database
- Gateway Service: GraphQL API over the other services using REST (mounted at `/graphql`)

Each service runs independently with its own FastAPI app and database. JWT is shared across services using a common secret or validated centrally via Auth (gRPC). The GraphQL gateway forwards Authorization headers to the underlying REST services.

## Quickstart

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
- Jobseeker: `BUSINESS_GRPC_ADDR` (default `localhost:50052`)
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

## API Overview

### GraphQL Gateway (port 8000)
Endpoint: `/graphql`
- Query `me` (requires Authorization header)
- Query `jobs`, `job(id)`
- Query `myApplications`, `myProfile` (requires Authorization header)
- Mutation `register(email, password, role)`
- Mutation `login(email, password)` returns `access_token`
- Mutation `upsertProfile(full_name, bio)` (requires Authorization header)
- Mutation `applyToJob(job_id, resume_text)` (requires Authorization header)
- Mutation `createCompany(name, description)` (business role)
- Mutation `createJob(title, description, location, is_active)` (business role)

The gateway forwards the `Authorization` header to the respective REST services.

### Other services
See sections above for each service’s REST endpoints.

## Notes
- With gRPC verification, services can have different `JWT_SECRET` values; only Auth’s secret is authoritative.
- The gateway uses REST calls and does not perform its own JWT validation.

## Troubleshooting
- If GraphQL calls fail, check the base URLs in the gateway `.env` and ensure the target services are running.

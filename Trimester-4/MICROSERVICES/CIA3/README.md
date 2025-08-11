# Job Board Microservices (FastAPI + PostgreSQL)

A minimal microservice-based Job Board application built with FastAPI and PostgreSQL. No Docker required.

Services:
- Auth Service: user registration, login, JWT issuance
- Jobseeker Service: jobseeker profiles and applications
- Business Service: company and job postings
- Admin Service: user management (roles) via auth database

Each service runs independently with its own FastAPI app and database. JWT is shared across services using a common secret.

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

Example using psql:
```sh
psql -U postgres -h localhost -c "CREATE DATABASE auth_db;"
psql -U postgres -h localhost -c "CREATE DATABASE jobseeker_db;"
psql -U postgres -h localhost -c "CREATE DATABASE business_db;"
```

### 3) Configure Environment
Copy `env.example` to `.env` inside each service folder and set values:
- `DATABASE_URL` for that service
- `JWT_SECRET` must be the same across all services

Connection string example:
```
DATABASE_URL=postgresql+psycopg2://postgres:YOUR_PASSWORD@localhost:5432/auth_db
JWT_SECRET=change_me_to_a_long_random_secret
JWT_EXPIRES_MINUTES=60
```

### 4) Install Dependencies
In four terminals (one per service), run:
```sh
cd services/auth_service
python -m venv .venv; .venv/Scripts/activate
pip install -r requirements.txt

cd ../jobseeker_service
python -m venv .venv; .venv/Scripts/activate
pip install -r requirements.txt

cd ../business_service
python -m venv .venv; .venv/Scripts/activate
pip install -r requirements.txt

cd ../admin_service
python -m venv .venv; .venv/Scripts/activate
pip install -r requirements.txt
```

### 5) Run Services
Use different ports:
```sh
# Auth (default: 8001)
uvicorn app.main:app --reload --port 8001

# Jobseeker (default: 8002)
uvicorn app.main:app --reload --port 8002

# Business (default: 8003)
uvicorn app.main:app --reload --port 8003

# Admin (default: 8004)
uvicorn app.main:app --reload --port 8004
```

On first run, each service will create its own tables automatically.

## API Overview

### Auth Service (port 8001)
- POST `/register` — Register a user `{email, password, role}` where role in `jobseeker|business|admin`
- POST `/login` — Login returns `{access_token}`
- GET `/me` — Current user (Bearer token required)

### Jobseeker Service (port 8002)
- GET `/profiles/me` — Get current jobseeker profile (role: jobseeker)
- PUT `/profiles/me` — Upsert profile `{full_name, bio}` (role: jobseeker)
- POST `/applications` — Create application `{job_id, resume_text}` (role: jobseeker)
- GET `/applications` — List my applications (role: jobseeker)

### Business Service (port 8003)
- POST `/companies` — Create or update company `{name, description}` (role: business)
- GET `/companies/me` — Get my company (role: business)
- POST `/jobs` — Create a job `{title, description, location, is_active}` (role: business)
- GET `/jobs` — Public list jobs
- GET `/jobs/{id}` — Public get job by id

### Admin Service (port 8004)
- GET `/users` — List users (role: admin)
- PATCH `/users/{user_id}/role` — Change user role `{role}` (role: admin)

## Notes
- All services share JWT secret for token validation. Set the same `JWT_SECRET` across `.env` files.
- This scaffold avoids inter-service HTTP calls for simplicity. E.g., jobseeker `job_id` is not validated against business service.
- For production, add Alembic migrations, proper config management, request tracing, and API gateway.

## Troubleshooting
- If you get connection errors, verify PostgreSQL is running and `DATABASE_URL` is correct.
- On Windows, ensure psycopg2-binary installs correctly; otherwise install Visual C++ Build Tools or use `psycopg[binary]`.

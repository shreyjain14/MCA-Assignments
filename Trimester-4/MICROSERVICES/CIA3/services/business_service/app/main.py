from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
import hashlib
import json
import grpc

from .db import Base, engine, get_db
from .models import Company, Job
from .schemas import CompanyUpsert, CompanyOut, JobCreate, JobOut
from .auth import require_business
from .config import settings

app = FastAPI(title="Business Service")


@app.on_event("startup")
def on_startup():
    Base.metadata.create_all(bind=engine)


@app.get("/_debug/jwt")
def debug_jwt_secret_hash():
    digest = hashlib.sha256(settings.jwt_secret.encode("utf-8")).hexdigest()
    return {"alg": "HS256", "secret_digest": digest, "auth_grpc_addr": settings.auth_grpc_addr}


@app.get("/_debug/auth_grpc")
def debug_auth_grpc_connectivity():
    try:
        with grpc.insecure_channel(settings.auth_grpc_addr) as channel:
            stub = channel.unary_unary(
                "/auth.AuthService/VerifyToken",
                request_serializer=lambda d: json.dumps(d).encode("utf-8"),
                response_deserializer=lambda b: json.loads(b.decode("utf-8")),
            )
            resp = stub({"token": ""})
            return {"reachable": True, "response": resp}
    except grpc.RpcError as e:
        return {"reachable": False, "error": str(e)}


@app.post("/companies", response_model=CompanyOut)
def upsert_company(payload: CompanyUpsert, actor=Depends(require_business), db: Session = Depends(get_db)):
    company = db.query(Company).filter(Company.user_id == actor.sub).first()
    if not company:
        company = Company(user_id=actor.sub, name=payload.name, description=payload.description)
        db.add(company)
    else:
        company.name = payload.name
        company.description = payload.description
    db.commit()
    db.refresh(company)
    return company


@app.get("/companies/me", response_model=CompanyOut)
def get_my_company(actor=Depends(require_business), db: Session = Depends(get_db)):
    company = db.query(Company).filter(Company.user_id == actor.sub).first()
    if not company:
        raise HTTPException(status_code=404, detail="Company not found")
    return company


@app.post("/jobs", response_model=JobOut)
def create_job(payload: JobCreate, actor=Depends(require_business), db: Session = Depends(get_db)):
    job = Job(owner_user_id=actor.sub, title=payload.title, description=payload.description, location=payload.location, is_active=payload.is_active)
    db.add(job)
    db.commit()
    db.refresh(job)
    return job


@app.get("/jobs", response_model=list[JobOut])
def list_jobs(db: Session = Depends(get_db)):
    jobs = db.query(Job).filter(Job.is_active == True).order_by(Job.created_at.desc()).all()
    return jobs


@app.get("/jobs/{job_id}", response_model=JobOut)
def get_job(job_id: int, db: Session = Depends(get_db)):
    job = db.query(Job).filter(Job.id == job_id).first()
    if not job:
        raise HTTPException(status_code=404, detail="Job not found")
    return job

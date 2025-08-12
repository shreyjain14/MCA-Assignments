from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
from datetime import datetime
import hashlib
import json
import grpc

from .db import Base, engine, get_db
from .models import JobseekerProfile, Application
from .schemas import ProfileUpsert, ProfileOut, ApplicationCreate, ApplicationOut
from .auth import require_jobseeker
from .config import settings

app = FastAPI(title="Jobseeker Service")


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


@app.get("/profiles/me", response_model=ProfileOut)
def get_my_profile(actor=Depends(require_jobseeker), db: Session = Depends(get_db)):
    profile = db.query(JobseekerProfile).filter(JobseekerProfile.user_id == actor.sub).first()
    if not profile:
        raise HTTPException(status_code=404, detail="Profile not found")
    return profile


@app.put("/profiles/me", response_model=ProfileOut)
def upsert_my_profile(payload: ProfileUpsert, actor=Depends(require_jobseeker), db: Session = Depends(get_db)):
    profile = db.query(JobseekerProfile).filter(JobseekerProfile.user_id == actor.sub).first()
    if not profile:
        profile = JobseekerProfile(user_id=actor.sub, full_name=payload.full_name, bio=payload.bio)
        db.add(profile)
    else:
        profile.full_name = payload.full_name
        profile.bio = payload.bio
        profile.updated_at = datetime.utcnow()
    db.commit()
    db.refresh(profile)
    return profile


@app.post("/applications", response_model=ApplicationOut)
def create_application(payload: ApplicationCreate, actor=Depends(require_jobseeker), db: Session = Depends(get_db)):
    application = Application(user_id=actor.sub, job_id=payload.job_id, resume_text=payload.resume_text)
    db.add(application)
    db.commit()
    db.refresh(application)
    return application


@app.get("/applications", response_model=list[ApplicationOut])
def list_my_applications(actor=Depends(require_jobseeker), db: Session = Depends(get_db)):
    apps = db.query(Application).filter(Application.user_id == actor.sub).order_by(Application.created_at.desc()).all()
    return apps

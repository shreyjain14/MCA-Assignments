from fastapi import FastAPI, Depends, HTTPException, status
from sqlalchemy.orm import Session
import hashlib

from .db import Base, engine, get_db
from .models import User, RoleEnum
from .schemas import UserCreate, UserLogin, UserOut, Token
from .auth import get_password_hash, verify_password, create_access_token, get_current_user
from .config import settings
from .grpc_server import GrpcServer

app = FastAPI(title="Auth Service")
_grpc: GrpcServer | None = None


@app.on_event("startup")
def on_startup():
    Base.metadata.create_all(bind=engine)
    global _grpc
    _grpc = GrpcServer(settings.grpc_bind)
    _grpc.start()


@app.on_event("shutdown")
def on_shutdown():
    global _grpc
    if _grpc:
        _grpc.stop()


@app.get("/_debug/jwt")
def debug_jwt_secret_hash():
    digest = hashlib.sha256(settings.jwt_secret.encode("utf-8")).hexdigest()
    return {"alg": "HS256", "secret_digest": digest, "grpc": settings.grpc_bind}


@app.post("/register", response_model=UserOut)
def register(payload: UserCreate, db: Session = Depends(get_db)):
    existing = db.query(User).filter(User.email == payload.email).first()
    if existing:
        raise HTTPException(status_code=status.HTTP_409_CONFLICT, detail="Email already registered")
    user = User(email=payload.email, password_hash=get_password_hash(payload.password), role=payload.role)
    db.add(user)
    db.commit()
    db.refresh(user)
    return user


@app.post("/login", response_model=Token)
def login(payload: UserLogin, db: Session = Depends(get_db)):
    user = db.query(User).filter(User.email == payload.email).first()
    if not user or not verify_password(payload.password, user.password_hash):
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid credentials")
    access_token = create_access_token(subject=user.id, email=user.email, role=user.role, expires_minutes=settings.jwt_expires_minutes)
    return {"access_token": access_token, "token_type": "bearer"}


@app.get("/me", response_model=UserOut)
def me(current_user: User = Depends(get_current_user)):
    return current_user

from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy.orm import Session
import hashlib
import json
import grpc

from .db import Base, engine, get_db
from .models import User
from .schemas import UserOut, RoleUpdate
from .auth import require_admin
from .config import settings

app = FastAPI(title="Admin Service")


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


@app.get("/users", response_model=list[UserOut])
def list_users(actor=Depends(require_admin), db: Session = Depends(get_db)):
    users = db.query(User).order_by(User.id.asc()).all()
    return users


@app.patch("/users/{user_id}/role", response_model=UserOut)
def change_role(user_id: int, payload: RoleUpdate, actor=Depends(require_admin), db: Session = Depends(get_db)):
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=404, detail="User not found")
    user.role = payload.role
    db.commit()
    db.refresh(user)
    return user

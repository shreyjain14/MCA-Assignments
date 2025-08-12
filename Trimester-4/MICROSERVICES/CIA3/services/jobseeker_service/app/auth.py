import json
import grpc
from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer

from .config import settings
from .schemas import TokenPayload

ALGORITHM = "HS256"
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/login")


def _verify_with_auth(token: str) -> TokenPayload:
    try:
        with grpc.insecure_channel(settings.auth_grpc_addr) as channel:
            stub = channel.unary_unary(
                "/auth.AuthService/VerifyToken",
                request_serializer=lambda d: json.dumps(d).encode("utf-8"),
                response_deserializer=lambda b: json.loads(b.decode("utf-8")),
            )
            resp = stub({"token": token})
            if not resp.get("valid"):
                raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail=resp.get("error", "Invalid token"))
            return TokenPayload(sub=resp["sub"], email=resp["email"], role=resp["role"])
    except grpc.RpcError:
        raise HTTPException(status_code=status.HTTP_503_SERVICE_UNAVAILABLE, detail="Auth service unavailable")


def require_jobseeker(token: str = Depends(oauth2_scheme)) -> TokenPayload:
    payload = _verify_with_auth(token)
    if payload.role != "jobseeker":
        raise HTTPException(status_code=status.HTTP_403_FORBIDDEN, detail="Jobseeker role required")
    return payload


def assert_job_exists(job_id: int) -> None:
    try:
        with grpc.insecure_channel(settings.business_grpc_addr) as channel:
            stub = channel.unary_unary(
                "/business.JobService/GetJob",
                request_serializer=lambda d: json.dumps(d).encode("utf-8"),
                response_deserializer=lambda b: json.loads(b.decode("utf-8")),
            )
            resp = stub({"job_id": job_id})
            if not resp.get("found"):
                raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Job not found")
    except grpc.RpcError:
        raise HTTPException(status_code=status.HTTP_503_SERVICE_UNAVAILABLE, detail="Business service unavailable")

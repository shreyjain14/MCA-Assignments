from datetime import datetime, timedelta
from typing import Optional

from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer
from jose import jwt, JWTError
from passlib.context import CryptContext
from sqlalchemy.orm import Session

from .config import settings
from .schemas import TokenPayload
from .db import get_db
from .models import User

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")
ALGORITHM = "HS256"
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/login")


def verify_password(plain_password: str, hashed_password: str) -> bool:
    return pwd_context.verify(plain_password, hashed_password)


def get_password_hash(password: str) -> str:
    return pwd_context.hash(password)


def create_access_token(*, subject: int, email: str, role: str, expires_minutes: int) -> str:
    expire = datetime.utcnow() + timedelta(minutes=expires_minutes)
    to_encode = {"sub": str(subject), "email": email, "role": role, "exp": expire}
    return jwt.encode(to_encode, settings.jwt_secret, algorithm=ALGORITHM)


def decode_token(token: str) -> TokenPayload:
    try:
        payload = jwt.decode(token, settings.jwt_secret, algorithms=[ALGORITHM])
        sub_raw = payload.get("sub")
        try:
            sub_val = int(sub_raw)
        except (TypeError, ValueError):
            raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid token subject")
        return TokenPayload(sub=sub_val, email=payload.get("email"), role=payload.get("role"))
    except JWTError:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="Invalid token")


def get_current_user(db: Session = Depends(get_db), token: str = Depends(oauth2_scheme)) -> User:
    payload = decode_token(token)
    user = db.query(User).filter(User.id == payload.sub).first()
    if not user:
        raise HTTPException(status_code=status.HTTP_401_UNAUTHORIZED, detail="User not found")
    return user

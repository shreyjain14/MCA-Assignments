from datetime import datetime
from sqlalchemy import Column, Integer, String, DateTime
from .db import Base


class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    email = Column(String(255), unique=True, nullable=False, index=True)
    password_hash = Column(String(255), nullable=False)
    role = Column(String(32), nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow, nullable=False)

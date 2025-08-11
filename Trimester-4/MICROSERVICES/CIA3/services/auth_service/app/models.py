from datetime import datetime
from sqlalchemy import Column, Integer, String, DateTime, Enum
from sqlalchemy.orm import validates

from .db import Base


class RoleEnum(str):
    JOBSEEKER = "jobseeker"
    BUSINESS = "business"
    ADMIN = "admin"


class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    email = Column(String(255), unique=True, nullable=False, index=True)
    password_hash = Column(String(255), nullable=False)
    role = Column(String(32), nullable=False, default=RoleEnum.JOBSEEKER)
    created_at = Column(DateTime, default=datetime.utcnow, nullable=False)

    @validates("role")
    def validate_role(self, key, value):
        if value not in {RoleEnum.JOBSEEKER, RoleEnum.BUSINESS, RoleEnum.ADMIN}:
            raise ValueError("Invalid role")
        return value

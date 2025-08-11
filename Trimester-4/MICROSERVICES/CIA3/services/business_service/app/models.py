from datetime import datetime
from sqlalchemy import Column, Integer, String, Text, Boolean, DateTime
from .db import Base


class Company(Base):
    __tablename__ = "companies"

    user_id = Column(Integer, primary_key=True, index=True)
    name = Column(String(255), nullable=False)
    description = Column(Text, nullable=True)


class Job(Base):
    __tablename__ = "jobs"

    id = Column(Integer, primary_key=True, index=True)
    owner_user_id = Column(Integer, index=True, nullable=False)
    title = Column(String(255), nullable=False)
    description = Column(Text, nullable=False)
    location = Column(String(255), nullable=True)
    is_active = Column(Boolean, default=True, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow, nullable=False)

from datetime import datetime
from sqlalchemy import Column, Integer, String, Text, DateTime
from .db import Base


class JobseekerProfile(Base):
    __tablename__ = "jobseeker_profiles"

    user_id = Column(Integer, primary_key=True, index=True)
    full_name = Column(String(255), nullable=False)
    bio = Column(Text, nullable=True)
    created_at = Column(DateTime, default=datetime.utcnow, nullable=False)
    updated_at = Column(DateTime, default=datetime.utcnow, nullable=False)


class Application(Base):
    __tablename__ = "applications"

    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer, index=True, nullable=False)
    job_id = Column(Integer, index=True, nullable=False)
    resume_text = Column(Text, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow, nullable=False)

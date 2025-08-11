from pydantic import BaseModel, Field
from typing import Optional, List


class ProfileUpsert(BaseModel):
    full_name: str = Field(min_length=1)
    bio: Optional[str] = None


class ProfileOut(BaseModel):
    user_id: int
    full_name: str
    bio: Optional[str] = None

    class Config:
        from_attributes = True


class ApplicationCreate(BaseModel):
    job_id: int
    resume_text: str


class ApplicationOut(BaseModel):
    id: int
    user_id: int
    job_id: int
    resume_text: str

    class Config:
        from_attributes = True


class TokenPayload(BaseModel):
    sub: int
    email: str
    role: str

from pydantic import BaseModel, Field
from typing import Optional


class CompanyUpsert(BaseModel):
    name: str = Field(min_length=1)
    description: Optional[str] = None


class CompanyOut(BaseModel):
    user_id: int
    name: str
    description: Optional[str] = None

    class Config:
        from_attributes = True


class JobCreate(BaseModel):
    title: str
    description: str
    location: Optional[str] = None
    is_active: bool = True


class JobOut(BaseModel):
    id: int
    owner_user_id: int
    title: str
    description: str
    location: Optional[str] = None
    is_active: bool

    class Config:
        from_attributes = True


class TokenPayload(BaseModel):
    sub: int
    email: str
    role: str

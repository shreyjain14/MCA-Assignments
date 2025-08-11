from pydantic import BaseModel, EmailStr, Field
from typing import Optional


class UserCreate(BaseModel):
    email: EmailStr
    password: str = Field(min_length=6)
    role: str = Field(pattern="^(jobseeker|business|admin)$")


class UserLogin(BaseModel):
    email: EmailStr
    password: str


class UserOut(BaseModel):
    id: int
    email: EmailStr
    role: str

    class Config:
        from_attributes = True


class Token(BaseModel):
    access_token: str
    token_type: str = "bearer"


class TokenPayload(BaseModel):
    sub: int
    email: EmailStr
    role: str

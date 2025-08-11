from pydantic import BaseModel, EmailStr, Field


class UserOut(BaseModel):
    id: int
    email: EmailStr
    role: str

    class Config:
        from_attributes = True


class RoleUpdate(BaseModel):
    role: str = Field(pattern="^(jobseeker|business|admin)$")


class TokenPayload(BaseModel):
    sub: int
    email: EmailStr
    role: str

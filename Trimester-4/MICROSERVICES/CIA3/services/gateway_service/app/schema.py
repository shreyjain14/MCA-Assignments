from __future__ import annotations

from typing import Optional, List
import strawberry
import httpx
from .config import settings
from .clients import auth_header


@strawberry.type
class User:
    id: int
    email: str
    role: str


@strawberry.type
class Job:
    id: int
    owner_user_id: int
    title: str
    description: str
    location: Optional[str]
    is_active: bool


@strawberry.type
class Profile:
    user_id: int
    full_name: str
    bio: Optional[str]


@strawberry.type
class Application:
    id: int
    user_id: int
    job_id: int
    resume_text: str


@strawberry.type
class Token:
    access_token: str
    token_type: str


async def _get(client: httpx.AsyncClient, url: str, authorization: Optional[str] = None):
    resp = await client.get(url, headers=auth_header(authorization))
    resp.raise_for_status()
    return resp.json()


async def _post(client: httpx.AsyncClient, url: str, json: dict, authorization: Optional[str] = None):
    resp = await client.post(url, json=json, headers=auth_header(authorization))
    resp.raise_for_status()
    return resp.json()


async def _put(client: httpx.AsyncClient, url: str, json: dict, authorization: Optional[str] = None):
    resp = await client.put(url, json=json, headers=auth_header(authorization))
    resp.raise_for_status()
    return resp.json()


@strawberry.type
class Query:
    @strawberry.field
    async def me(self, info) -> Optional[User]:
        client: httpx.AsyncClient = info.context["client"]
        authorization: Optional[str] = info.context.get("authorization")
        try:
            data = await _get(client, f"{settings.auth_base_url}/me", authorization)
            return User(**data)
        except httpx.HTTPStatusError as exc:
            return None

    @strawberry.field
    async def jobs(self, info) -> List[Job]:
        client: httpx.AsyncClient = info.context["client"]
        data = await _get(client, f"{settings.business_base_url}/jobs")
        return [Job(**item) for item in data]

    @strawberry.field
    async def job(self, info, id: int) -> Optional[Job]:
        client: httpx.AsyncClient = info.context["client"]
        try:
            data = await _get(client, f"{settings.business_base_url}/jobs/{id}")
            return Job(**data)
        except httpx.HTTPStatusError:
            return None

    @strawberry.field
    async def my_applications(self, info) -> List[Application]:
        client: httpx.AsyncClient = info.context["client"]
        authorization: Optional[str] = info.context.get("authorization")
        data = await _get(client, f"{settings.jobseeker_base_url}/applications", authorization)
        return [Application(**item) for item in data]

    @strawberry.field
    async def my_profile(self, info) -> Optional[Profile]:
        client: httpx.AsyncClient = info.context["client"]
        authorization: Optional[str] = info.context.get("authorization")
        try:
            data = await _get(client, f"{settings.jobseeker_base_url}/profiles/me", authorization)
            return Profile(**data)
        except httpx.HTTPStatusError:
            return None


@strawberry.type
class Mutation:
    @strawberry.mutation
    async def register(self, info, email: str, password: str, role: str) -> User:
        client: httpx.AsyncClient = info.context["client"]
        data = await _post(client, f"{settings.auth_base_url}/register", {"email": email, "password": password, "role": role})
        return User(**data)

    @strawberry.mutation
    async def login(self, info, email: str, password: str) -> Token:
        client: httpx.AsyncClient = info.context["client"]
        data = await _post(client, f"{settings.auth_base_url}/login", {"email": email, "password": password})
        return Token(**data)

    @strawberry.mutation
    async def upsert_profile(self, info, full_name: str, bio: Optional[str] = None) -> Profile:
        client: httpx.AsyncClient = info.context["client"]
        authorization: Optional[str] = info.context.get("authorization")
        data = await _put(client, f"{settings.jobseeker_base_url}/profiles/me", {"full_name": full_name, "bio": bio}, authorization)
        return Profile(**data)

    @strawberry.mutation
    async def apply_to_job(self, info, job_id: int, resume_text: str) -> Application:
        client: httpx.AsyncClient = info.context["client"]
        authorization: Optional[str] = info.context.get("authorization")
        data = await _post(client, f"{settings.jobseeker_base_url}/applications", {"job_id": job_id, "resume_text": resume_text}, authorization)
        return Application(**data)

    @strawberry.mutation
    async def create_company(self, info, name: str, description: Optional[str] = None) -> "Company":
        client: httpx.AsyncClient = info.context["client"]
        authorization: Optional[str] = info.context.get("authorization")
        data = await _post(client, f"{settings.business_base_url}/companies", {"name": name, "description": description}, authorization)
        return Company(**data)

    @strawberry.mutation
    async def create_job(self, info, title: str, description: str, location: Optional[str] = None, is_active: bool = True) -> Job:
        client: httpx.AsyncClient = info.context["client"]
        authorization: Optional[str] = info.context.get("authorization")
        data = await _post(client, f"{settings.business_base_url}/jobs", {"title": title, "description": description, "location": location, "is_active": is_active}, authorization)
        return Job(**data)


@strawberry.type
class Company:
    user_id: int
    name: str
    description: Optional[str]


schema = strawberry.Schema(query=Query, mutation=Mutation)

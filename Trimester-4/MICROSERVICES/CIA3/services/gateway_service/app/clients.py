from typing import AsyncIterator, Optional, Mapping
import httpx


async def create_client() -> AsyncIterator[httpx.AsyncClient]:
    async with httpx.AsyncClient(timeout=10.0) as client:
        yield client


def auth_header(authorization: Optional[str]) -> dict[str, str]:
    headers: dict[str, str] = {}
    if authorization:
        headers["Authorization"] = authorization
    return headers

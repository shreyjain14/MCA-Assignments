from fastapi import FastAPI, Request
from fastapi.middleware.cors import CORSMiddleware
from strawberry.fastapi import GraphQLRouter
import strawberry
import httpx

from .schema import schema
from .clients import create_client
from .config import settings

app = FastAPI(title="GraphQL Gateway")

app.add_middleware(
    CORSMiddleware,
    allow_origins=settings.cors_origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)


async def get_context(request: Request):
    authorization = request.headers.get("authorization")
    client_cm = create_client()
    client = await client_cm.__anext__()

    async def finalizer():
        try:
            await client.aclose()
        except Exception:
            pass

    request.state._client_finalizer = finalizer
    return {"request": request, "client": client, "authorization": authorization}


gql_router = GraphQLRouter(schema, context_getter=get_context)
app.include_router(gql_router, prefix="/graphql")


@app.on_event("shutdown")
async def _shutdown():
    pass

from fastapi import FastAPI, Request
from strawberry.fastapi import GraphQLRouter
import strawberry
import httpx

from .schema import schema
from .clients import create_client

app = FastAPI(title="GraphQL Gateway")


async def get_context(request: Request):
    # Create an httpx client per request and pass Authorization header through
    authorization = request.headers.get("authorization")
    client_cm = create_client()
    client = await client_cm.__anext__()

    async def finalizer():
        # Close client when request finishes
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
    # Best-effort close of any client created in context
    # (GraphQLRouter doesn't support lifespan per op, so we close in shutdown as fallback)
    pass

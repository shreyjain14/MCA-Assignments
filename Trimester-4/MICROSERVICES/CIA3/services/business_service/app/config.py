from pydantic_settings import BaseSettings, SettingsConfigDict
from pydantic import Field


class Settings(BaseSettings):
    database_url: str = Field(alias="DATABASE_URL")
    jwt_secret: str = Field(alias="JWT_SECRET")
    jwt_expires_minutes: int = Field(60, alias="JWT_EXPIRES_MINUTES")
    app_name: str = "Business Service"
    auth_grpc_addr: str = Field("localhost:50051", alias="AUTH_GRPC_ADDR")
    grpc_bind: str = Field("0.0.0.0:50052", alias="GRPC_BIND")

    model_config = SettingsConfigDict(env_file=".env", case_sensitive=False)


settings = Settings()

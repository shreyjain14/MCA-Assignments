from pydantic_settings import BaseSettings, SettingsConfigDict
from pydantic import Field


class Settings(BaseSettings):
    auth_base_url: str = Field(alias="AUTH_BASE_URL")
    jobseeker_base_url: str = Field(alias="JOBSEEKER_BASE_URL")
    business_base_url: str = Field(alias="BUSINESS_BASE_URL")
    admin_base_url: str = Field(alias="ADMIN_BASE_URL")
    app_name: str = "GraphQL Gateway"

    model_config = SettingsConfigDict(env_file=".env", case_sensitive=False)


settings = Settings()

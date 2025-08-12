from pydantic_settings import BaseSettings, SettingsConfigDict
from pydantic import Field


class Settings(BaseSettings):
    auth_base_url: str = Field(alias="AUTH_BASE_URL")
    jobseeker_base_url: str = Field(alias="JOBSEEKER_BASE_URL")
    business_base_url: str = Field(alias="BUSINESS_BASE_URL")
    admin_base_url: str = Field(alias="ADMIN_BASE_URL")
    app_name: str = "GraphQL Gateway"
    cors_origins_raw: str = Field("http://localhost:3000", alias="CORS_ORIGINS")

    model_config = SettingsConfigDict(env_file=".env", case_sensitive=False)

    @property
    def cors_origins(self) -> list[str]:
        return [o.strip() for o in self.cors_origins_raw.split(",") if o.strip()]


settings = Settings()

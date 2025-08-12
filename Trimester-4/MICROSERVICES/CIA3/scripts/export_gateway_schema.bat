@echo off
setlocal

set SCRIPT_DIR=%~dp0
cd /d "%SCRIPT_DIR%..\services\gateway_service"

if not exist .venv\Scripts\python.exe (
  python -m venv .venv
)

call .venv\Scripts\activate.bat
pip install -r requirements.txt

python export_schema.py export

echo Wrote export\schema.introspection.json and export\schema.graphql

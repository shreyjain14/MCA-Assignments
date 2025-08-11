@echo off
setlocal

rem Navigate to service directory
set SCRIPT_DIR=%~dp0
cd /d "%SCRIPT_DIR%..\services\auth_service"

rem Create venv if missing
if not exist .venv\Scripts\python.exe (
  python -m venv .venv
)

call .venv\Scripts\activate.bat
pip install -r requirements.txt

python -m uvicorn app.main:app --reload --port 8001

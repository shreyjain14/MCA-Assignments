@echo off
setlocal

set SCRIPT_DIR=%~dp0
cd /d "%SCRIPT_DIR%..\services\admin_service"

if not exist .venv\Scripts\python.exe (
  python -m venv .venv
)

call .venv\Scripts\activate.bat
pip install -r requirements.txt

python -m uvicorn app.main:app --reload --port 8004

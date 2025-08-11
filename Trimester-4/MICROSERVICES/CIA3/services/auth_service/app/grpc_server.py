import json
import threading
from concurrent import futures

import grpc
from fastapi import HTTPException

from .auth import decode_token
from .db import SessionLocal
from .models import User

SERVICE_NAME = "auth.AuthService"
METHOD_VERIFY = "/auth.AuthService/VerifyToken"


def _verify_token_handler(request_bytes: bytes, context) -> bytes:
    try:
        data = json.loads(request_bytes.decode("utf-8"))
        token = data.get("token")
        if not token:
            return json.dumps({"valid": False, "error": "token required"}).encode("utf-8")

        payload = decode_token(token)

        db = SessionLocal()
        try:
            user = db.query(User).filter(User.id == payload.sub).first()
            if not user:
                return json.dumps({"valid": False, "error": "user not found"}).encode("utf-8")
            # Return authoritative role and email from DB
            return json.dumps({
                "valid": True,
                "sub": user.id,
                "email": user.email,
                "role": user.role,
                "error": None,
            }).encode("utf-8")
        finally:
            db.close()

    except HTTPException as exc:
        return json.dumps({"valid": False, "error": exc.detail}).encode("utf-8")
    except Exception:  # noqa: BLE001
        return json.dumps({"valid": False, "error": "internal error"}).encode("utf-8")


def _create_server() -> grpc.Server:
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4))

    method_handlers = {
        "VerifyToken": grpc.unary_unary_rpc_method_handler(
            _verify_token_handler,
            request_deserializer=lambda x: x,  # raw bytes
            response_serializer=lambda x: x,   # raw bytes
        )
    }

    generic_handler = grpc.method_handlers_generic_handler(SERVICE_NAME, method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))
    return server


class GrpcServer:
    def __init__(self, bind_addr: str):
        self.bind_addr = bind_addr
        self.server = _create_server()
        self._thread: threading.Thread | None = None

    def start(self) -> None:
        self.server.add_insecure_port(self.bind_addr)
        self.server.start()
        # Keep server alive in a thread so FastAPI can run
        self._thread = threading.Thread(target=self.server.wait_for_termination, daemon=True)
        self._thread.start()

    def stop(self, grace: float = 1.0) -> None:
        self.server.stop(grace)
        if self._thread and self._thread.is_alive():
            self._thread.join(timeout=grace + 0.5)

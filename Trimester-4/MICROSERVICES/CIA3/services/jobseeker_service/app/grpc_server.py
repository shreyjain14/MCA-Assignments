import json
import threading
from concurrent import futures

import grpc

from .db import SessionLocal
from .models import Application

SERVICE_NAME = "jobseeker.ApplicationService"


def _list_applications_for_job_handler(request_bytes: bytes, context) -> bytes:
    try:
        data = json.loads(request_bytes.decode("utf-8"))
        job_id = data.get("job_id")
        if not isinstance(job_id, int):
            return json.dumps({"applications": [], "error": "job_id required"}).encode("utf-8")
        db = SessionLocal()
        try:
            rows = db.query(Application).filter(Application.job_id == job_id).order_by(Application.created_at.desc()).all()
            apps = [
                {
                    "id": a.id,
                    "user_id": a.user_id,
                    "job_id": a.job_id,
                    "resume_text": a.resume_text,
                }
                for a in rows
            ]
            return json.dumps({"applications": apps}).encode("utf-8")
        finally:
            db.close()
    except Exception:
        return json.dumps({"applications": [], "error": "internal error"}).encode("utf-8")


def _create_server() -> grpc.Server:
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4))
    method_handlers = {
        "ListApplicationsForJob": grpc.unary_unary_rpc_method_handler(
            _list_applications_for_job_handler,
            request_deserializer=lambda x: x,
            response_serializer=lambda x: x,
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
        self._thread = threading.Thread(target=self.server.wait_for_termination, daemon=True)
        self._thread.start()

    def stop(self, grace: float = 1.0) -> None:
        self.server.stop(grace)
        if self._thread and self._thread.is_alive():
            self._thread.join(timeout=grace + 0.5)

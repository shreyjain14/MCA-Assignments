import json
import threading
from concurrent import futures

import grpc

from .db import SessionLocal
from .models import Job

SERVICE_NAME = "business.JobService"


def _get_job_handler(request_bytes: bytes, context) -> bytes:
    try:
        data = json.loads(request_bytes.decode("utf-8"))
        job_id = data.get("job_id")
        if not isinstance(job_id, int):
            return json.dumps({"found": False, "error": "job_id required"}).encode("utf-8")
        db = SessionLocal()
        try:
            job = db.query(Job).filter(Job.id == job_id, Job.is_active == True).first()
            if not job:
                return json.dumps({"found": False}).encode("utf-8")
            return json.dumps({
                "found": True,
                "job": {
                    "id": job.id,
                    "owner_user_id": job.owner_user_id,
                    "title": job.title,
                    "is_active": job.is_active,
                }
            }).encode("utf-8")
        finally:
            db.close()
    except Exception:
        return json.dumps({"found": False, "error": "internal error"}).encode("utf-8")


def _create_server() -> grpc.Server:
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4))
    method_handlers = {
        "GetJob": grpc.unary_unary_rpc_method_handler(
            _get_job_handler,
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

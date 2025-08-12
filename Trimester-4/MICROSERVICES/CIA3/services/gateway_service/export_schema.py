import json
import sys
from pathlib import Path

from graphql import get_introspection_query

# Import Strawberry schema
from app.schema import schema


def main(out_dir: str = ".") -> None:
    out_path = Path(out_dir)
    out_path.mkdir(parents=True, exist_ok=True)

    # Introspection JSON (wrapped with top-level "data")
    introspection_query = get_introspection_query(descriptions=True, schema_description=True)
    result = schema.execute_sync(introspection_query)
    if result.errors:
        for err in result.errors:
            print(f"Error: {err}", file=sys.stderr)
        sys.exit(1)

    introspection_file = out_path / "schema.introspection.json"
    with introspection_file.open("w", encoding="utf-8") as f:
        json.dump({"data": result.data}, f, ensure_ascii=False, indent=2)

    # SDL schema
    sdl = schema.as_str()
    sdl_file = out_path / "schema.graphql"
    with sdl_file.open("w", encoding="utf-8") as f:
        f.write(sdl)

    print(f"Wrote {introspection_file.resolve()} and {sdl_file.resolve()}")


if __name__ == "__main__":
    directory = sys.argv[1] if len(sys.argv) > 1 else "."
    main(directory)

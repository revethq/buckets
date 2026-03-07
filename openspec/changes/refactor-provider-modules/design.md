## Context

The `add-bucket-service` proposal defines a 3-module structure (`core`, `web`, `persistence-runtime`) with all storage provider client implementations in `web`. This design revises the module structure to isolate each provider into its own sub-project.

## Goals / Non-Goals

- **Goals:**
  - Each storage provider in its own Gradle module under `provider-*`
  - Provider modules depend only on `core`
  - Runtime discovery of available providers via CDI
  - Deployments include only the provider SDKs they need

- **Non-Goals:**
  - Changing the `StorageProviderClient` interface or storage operations
  - Changing the REST API, persistence, or encryption design
  - SPI/ServiceLoader — CDI is sufficient for Quarkus

## Decisions

### Module Structure
- **Decision:** Six modules — `core`, `web`, `persistence-runtime`, `provider-s3`, `provider-gcs`, `provider-azure-blob`
- **Rationale:** Provider SDKs are large and unrelated. Separating them avoids pulling GCS dependencies into a deployment that only uses S3. Module names use `provider-` prefix for clear grouping.

### Provider Module → Core Dependency Only
- **Decision:** Each `provider-*` module depends only on `core`
- **Rationale:** Providers implement the `StorageProviderClient` interface defined in `core`. They have no need for persistence or web concerns.

### CDI-Based Provider Discovery
- **Decision:** `StorageClientFactory` discovers `StorageProviderClient` implementations via CDI `Instance<StorageProviderClient>` injection
- **Rationale:** Quarkus natively supports CDI bean discovery across modules on the classpath. No configuration needed — include the module, the provider is available.

### S3 and MinIO Shared Module
- **Decision:** `provider-s3` handles both S3 and MinIO providers
- **Rationale:** MinIO uses the S3-compatible API via AWS SDK v2 with an endpoint override. They share the same client implementation and SDK dependency.

## Risks / Trade-offs

- **Risk:** Consumers must remember to include provider modules on the classpath.
  - **Mitigation:** Build scripts and documentation will list which modules to include. `StorageClientFactory` can throw a clear error when no provider matches a bucket's provider type.

- **Risk:** Additional Gradle modules add build complexity.
  - **Mitigation:** Each provider module is small (one implementation class + SDK dependency). The build config is minimal.

## Updated Module Dependency Graph

```
core (no Quarkus dependency)
├── persistence-runtime (core)
├── web (core)
├── provider-s3 (core)       — AWS SDK v2
├── provider-gcs (core)      — Google Cloud Storage
└── provider-azure-blob (core) — Azure Blob SDK (stub)
```

## Open Questions

- None

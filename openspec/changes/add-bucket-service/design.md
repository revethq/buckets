## Context

The documents service has an embedded bucket subsystem handling cloud storage configuration, credential management, and presigned URL generation. This design extracts that into a standalone multi-module project so it can be shared across services.

The new project follows revet-quarkus conventions: Quarkus 3.31.1, Kotlin 2.3.10, Gradle 9.3.1, JVM 25.

## Goals / Non-Goals

- **Goals:**
  - Standalone bucket CRUD with encrypted credential storage
  - Multi-provider support (S3, GCS, MinIO) with presigned URL generation
  - IAM-gated REST API using `buckets:` permission prefix
  - Multi-module structure: `core` (domain), `web` (REST), `persistence-runtime` (Hibernate)

- **Non-Goals:**
  - File upload orchestration (remains in documents service)
  - Document version management (remains in documents service)
  - Azure Blob implementation (deferred)
  - Organization-to-bucket mapping (consumer responsibility)

## Decisions

### Module Structure
- **Decision:** Six modules — `core`, `web`, `persistence-runtime`, `provider-s3`, `provider-gcs`, `provider-azure-blob`
- **Rationale:** Follows revet-quarkus standard. Module names are unprefixed since the root project is already `buckets`. Core has no Quarkus dependency. Persistence and web depend only on core, not on each other. Each storage provider is isolated in its own module (`provider-*`) depending only on `core`, so deployments include only the SDKs they need. Provider implementations are discovered at runtime via CDI.

### Module Dependency Graph
```
core (no Quarkus dependency)
├── persistence-runtime (core)
├── web (core)
├── provider-s3 (core)       — AWS SDK v2
├── provider-gcs (core)      — Google Cloud Storage
└── provider-azure-blob (core) — Azure Blob SDK (stub)
```

### Table Naming
- **Decision:** Use `revet_buckets` table name
- **Rationale:** Aligns with revet project naming conventions.

### Permission Prefix
- **Decision:** Use `buckets:` prefix for all actions (e.g., `buckets:ListBuckets`)
- **Rationale:** Service-scoped permissions — this is the buckets service, not documents.

### URN Format
- **Decision:** `urn:revet:buckets:{tenantId}:bucket/{bucketUuid}`
- **Rationale:** Service name in URN matches the service identity.

### Encryption Config
- **Decision:** Config property `revet.encryption.key`
- **Rationale:** Consistent revet branding. Environment variable: `REVET_ENCRYPTION_KEY`.

### Storage Provider Clients
- **Decision:** S3/MinIO share one client (AWS SDK v2) in `provider-s3`, GCS uses Google Cloud Storage library in `provider-gcs`. Azure Blob throws `UnsupportedOperationException` as a stub in `provider-azure-blob`. `StorageClientFactory` in `web` discovers available providers via CDI `Instance<StorageProviderClient>` injection.
- **Rationale:** Matches proven implementation from documents service. Provider SDKs are large and unrelated — separating them avoids pulling GCS dependencies into a deployment that only uses S3. CDI bean discovery across modules requires no additional configuration in Quarkus. Azure deferred until needed.

### Credential Security
- **Decision:** AES-256-GCM encryption at rest, credentials never in API responses.
- **Rationale:** Carries forward the security model from documents.

## Risks / Trade-offs

- **Risk:** Documents service must be updated to consume buckets as a dependency instead of its embedded code.
  - **Mitigation:** This is a future concern — documents migration is out of scope for this proposal.

- **Risk:** Quarkus version mismatch between documents (3.17.4) and buckets (3.31.1).
  - **Mitigation:** Buckets is a standalone service. When documents migrates, it will need to align versions.

## Open Questions

- None — scope is well-defined from the existing documents implementation.

# Change: Add Bucket Service

## Why
The documents service currently embeds bucket management (storage configuration, credentials, presigned URLs) directly. Extracting buckets into a standalone service allows multiple services to share storage configuration and decouples storage concerns from document management.

## What Changes
- New multi-module Kotlin/Quarkus project following revet-quarkus conventions (`core`, `web`, `persistence-runtime`)
- Domain model: `Bucket`, `StorageProvider` enum (S3, GCS, MINIO, AZURE_BLOB)
- REST API: CRUD at `/api/v1/buckets` with IAM permission gating
- Persistence: Hibernate Panache entities with `revet_buckets` table
- Credential encryption: AES-256-GCM for accessKey/secretKey at rest
- Storage provider clients: S3/MinIO (AWS SDK v2), GCS (Google Cloud Storage)
- Presigned URL generation for uploads and downloads
- Permissions use `buckets:` prefix (e.g., `buckets:ListBuckets`)
- URN format: `urn:revet:buckets:{tenantId}:bucket/{bucketUuid}`

## Impact
- Affected specs: None (greenfield project)
- Affected code: New project — no existing code modified
- Future impact: Documents service will consume this as a dependency, replacing its embedded bucket code
